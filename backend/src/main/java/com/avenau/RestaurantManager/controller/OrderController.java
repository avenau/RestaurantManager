package com.avenau.RestaurantManager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avenau.RestaurantManager.models.Discount;
import com.avenau.RestaurantManager.models.Food;
import com.avenau.RestaurantManager.models.FoodOrder;
import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.DiscountService;
import com.avenau.RestaurantManager.service.FoodService;
import com.avenau.RestaurantManager.service.OrderService;
import com.avenau.RestaurantManager.service.UserService;

@Controller
public class OrderController {

	
	private UserService userService;
	private DiscountService discountService;
	private FoodService foodService;
	private OrderService orderService;
	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
	
	public OrderController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public OrderController(UserService userService, DiscountService discountService, FoodService foodService,
			OrderService orderService) {
		super();
		this.userService = userService;
		this.discountService = discountService;
		this.foodService = foodService;
		this.orderService = orderService;
	}
	
/*
 * ==============================================================================================
 * 									DISCOUNT STUFF
 * ==============================================================================================
 */
	
	/**
	 * If the discount code is empty, and error message is produced.
	 * If the discount is not in number form an error will be produced
	 * If there are no errors the discount will be persisted into the database
	 * @param discountCode
	 * @param discount
	 * @param model
	 * @return manage discount page
	 */
	@PostMapping("/addDiscount")
	public String addDiscount(@RequestParam String discountCode, @RequestParam String discount, Model model) {
		LOGGER.info("Adding Discount!");
		if (discountCode == "") {
			LOGGER.debug("Empty Discount Code");
			model.addAttribute("errorMessage", "Please Enter Valid Inputs!");
			model.addAttribute("allDiscounts", discountService.findAll());
			return "manageDiscounts";
		}
		
		try {
			double realDiscount = Double.parseDouble(discount);

			Discount newDiscount = new Discount(realDiscount, discountCode);
			discountService.save(newDiscount);
		} catch (NumberFormatException e) {
			LOGGER.debug("Non Numeric Discount");
			model.addAttribute("errorMessage", "Please Enter Valid Inputs!");
			model.addAttribute("allDiscounts", discountService.findAll());
			return "manageDiscounts";
		}

		model.addAttribute("allDiscounts", discountService.findAll());
		return "manageDiscounts";
	}

	/**
	 * Deletes discount from database and returns users back to manageDiscount page
	 * @param discountId
	 * @param session
	 * @param model
	 * @return manage discount page
	 */
	@PostMapping("/deleteDiscount")
	public String deleteDiscount(@RequestParam int discountId, HttpSession session, Model model) {
		LOGGER.info("Deleting Discount!");
		Optional<Discount> discount = discountService.find(discountId);
		if (discount == null) {
			LOGGER.debug("Discount doesnt exist!");
		} else if (discount.isPresent()) {
			LOGGER.debug("Discount exist");
			discountService.delete(discount.get());
		}
		
		model.addAttribute("allDiscounts", discountService.findAll());
		return "manageDiscounts";
	}
	
	/**
	 * If the discount doesnt exist, then an invalid discount message will be produced
	 * If the discount is already used in the order then an "already used this discount" message will be produced
	 * If all is well the discount will be added to the order
	 * @param discount
	 * @param session
	 * @param model
	 * @return make an order page
	 */
	@PostMapping("/addDiscountToOrder")
	public String addDiscountToOrder(@RequestParam String discount, HttpSession session, Model model) {
		LOGGER.info("Adding Discount to Order!");
		if (!discountService.find(discount).isPresent()) {
			LOGGER.debug("Discount doesnt exist");
			model.addAttribute("orderPrice", model.getAttribute("orderPrice"));
			model.addAttribute("allFoods", foodService.findAll());
			model.addAttribute("errorMessage", "Input a valid Discount");
			return "orderPage";			
		}
		Discount recoveredDiscount = discountService.find(discount).get();
		List<Discount> currentDiscount = (List<Discount>) session.getAttribute("discounts");
		List<Food> currentOrder = (List<Food>) session.getAttribute("order");
		
		if (currentDiscount == null) {
			LOGGER.debug("Session Discount list is empty!");
			currentDiscount = new ArrayList<Discount>();
		}
		if (currentOrder == null) {
			LOGGER.debug("Session Food List is empty!");
			currentOrder = new ArrayList<Food>();
		}
		
		if (isDiscountUsed(currentDiscount, recoveredDiscount)) {
			LOGGER.debug("Discount already used!");
			model.addAttribute("orderPrice", model.getAttribute("orderPrice"));
			model.addAttribute("allFoods", foodService.findAll());
			model.addAttribute("errorMessage", "You already used this discount in this order!");
			return "orderPage";
			
		}
		currentDiscount.add(recoveredDiscount);
		session.setAttribute("discounts", currentDiscount);
		
		double price = calculatePrice(currentOrder, currentDiscount);
			
		model.addAttribute("orderPrice", price);
		model.addAttribute("allFoods", foodService.findAll());
		
		
		return "orderPage";
	}
	
/*
 * =============================================================================================
 * 									ORDER FUNCTIONS
 * =============================================================================================
 */
	
	/**
	 * Add food into food order
	 * @param food_id
	 * @param session
	 * @param model
	 * @return order page
	 */
	@PostMapping("/addToOrder")
	public String addToOrder(@RequestParam("foods") int food_id, HttpSession session, Model model) {
		LOGGER.info("Adding Food To Order!");
		List<Discount> currentDiscount = (List<Discount>) session.getAttribute("discounts");
		Food selectedFood = foodService.find(food_id);
		List<Food> currentOrder = (List<Food>) session.getAttribute("order");
		if (currentOrder == null) {
			LOGGER.debug("Session Order list is empty!");
			currentOrder = new ArrayList<Food>();
		}
		if (currentDiscount == null) {
			LOGGER.debug("Session Discount list is empty!");
			currentDiscount = new ArrayList<Discount>();
		}
		currentOrder.add(selectedFood);
		session.setAttribute("order", currentOrder);
		model.addAttribute("allFoods", foodService.findAll());
		
		double totalPrice = calculatePrice(currentOrder, currentDiscount);
		model.addAttribute("orderPrice", totalPrice);
		
		return "orderPage";
	}
	
	/**
	 * If there is not food in the order, the order will be be complete
	 * Persists the order into the database
	 * @param session
	 * @param model
	 * @return order page if there is an error, if successful, will return confirm page
	 */
	@GetMapping("/completeOrder")
	public String completeOrder(HttpSession session, Model model) {
		LOGGER.info("Completing Order!");

		List<Food> orderFoodList = (List<Food>) session.getAttribute("order");
		List<Discount> orderDiscountList = (List<Discount>) session.getAttribute("discounts");
		if (orderFoodList == null) {
			LOGGER.debug("Food list is empty!");
			model.addAttribute("orderPrice", 0.0);
			model.addAttribute("allFoods", foodService.findAll());
			model.addAttribute("errorMessage", "Please add a food item!");
			return "orderPage";
		}
		if (orderDiscountList == null) {
			LOGGER.debug("Session Discount list is empty!");
			orderDiscountList = new ArrayList<Discount>();
		}
		
		
		User currentCustomer = (User) session.getAttribute(UserController.CURRENT_USER);

		FoodOrder newOrder = new FoodOrder(currentCustomer, orderFoodList, orderDiscountList);
		model.addAttribute("currentOrder", newOrder);
		orderService.save(newOrder);
		currentCustomer.addOrder(newOrder);
		userService.save(currentCustomer);
		
		
		session.removeAttribute("order");
		session.removeAttribute("discounts");
		return "orderConfirm";
	}
	
	/**
	 * Clears all the food from the current order
	 * @param session
	 * @param model
	 * @return order page
	 */
	@GetMapping("/clearOrder")
	public String clearOrder(HttpSession session, Model model) {
		LOGGER.info("Clearing Order!");
		session.removeAttribute("order");
		session.removeAttribute("discounts");
		model.addAttribute("allFoods", foodService.findAll());
		model.addAttribute("orderPrice", 0.0);
		return "orderPage";
	}
	
	/**
	 * Changes the order status of an order
	 * @param orderId
	 * @param orderStatus
	 * @param session
	 * @param model
	 * @return manage order page
	 */
	@PostMapping("/changeOrderStatus")
	public String changeOrderStatus(@RequestParam int orderId, @RequestParam String orderStatus, HttpSession session, Model model) {
		LOGGER.info("Changing Order Status!");
		FoodOrder order = orderService.find(orderId);
		order.setOrderStatus(orderStatus);
		orderService.save(order);
		model.addAttribute("allOrders", orderService.findAll());
		return "manageOrders";
	}
	
	/**
	 * Delete an order by id
	 * @param deleteId
	 * @param session
	 * @param model
	 * @return manage order page
	 */
	@PostMapping("/deleteOrder")
	public String deleteOrder(@RequestParam int deleteId, HttpSession session, Model model) {
		LOGGER.info("Deleting Order!");
		FoodOrder order = orderService.find(deleteId);
		orderService.delete(order);
		model.addAttribute("allOrders", orderService.findAll());
		return "manageOrders";
	}
	
	/**
	 * User deletes their own order
	 * @param deleteId
	 * @param session
	 * @param model
	 * @return user order page
	 */
	@PostMapping("/deleteMyOrder")
	public String deleteMyOrder(@RequestParam int deleteId, HttpSession session, Model model) {
		LOGGER.info("Deleting Own Order!");
		FoodOrder order = orderService.find(deleteId);
		orderService.delete(order);
		User newUser = userService.find(order.getOrderedBy().getUser_id());
		session.setAttribute(UserController.CURRENT_USER, newUser);
		model.addAttribute("userOrders", newUser.getOrderList());
		return "userOrders";
	}
	
	
/*
 * =============================================================================================
 * 										FOOD FUNCTIONS
 * =============================================================================================
 */
	
	/**
	 * Add a new food to the menu.
	 * Persists a food item into the database
	 * If the food name is empty, an error message is produced
	 * If the price is not a number, an error message will be produced
	 * @param name
	 * @param price
	 * @param model
	 * @return manage food
	 */
	@PostMapping("/addFood")
	public String addFood(@RequestParam String name, @RequestParam String price, Model model) {
		LOGGER.info("Adding Food!");
		if (name == "") {
			LOGGER.debug("Food name is empty!");
			model.addAttribute("errorMessage", "Please Enter Valid Inputs!");
			model.addAttribute("allFoods", foodService.findAll());
			return "manageFood";
		}
		try {
			double realPrice = Double.parseDouble(price);

			Food newFood = new Food(name, realPrice);
			foodService.save(newFood);
		} catch (NumberFormatException e) {
			LOGGER.debug("Price is not a number!");
			model.addAttribute("errorMessage", "Please Enter Valid Inputs!");
			model.addAttribute("allFoods", foodService.findAll());
			return "manageFood";
		}

		model.addAttribute("allFoods", foodService.findAll());
		return "manageFood";
	}
	
	/**
	 * Delete food item from the database
	 * @param foodId
	 * @param model
	 * @return manage food page
	 */
	@PostMapping("/deleteFood")
	public String deleteFood(@RequestParam int foodId, Model model) {
		LOGGER.info("Deleting Food!");
		Food food = foodService.find(foodId);
		foodService.remove(food);
		model.addAttribute("allFoods", foodService.findAll());
		return "manageFood";
	}	
	

/*
 * =============================================================================================
 * 									HELPER FUNCTIONS
 * =============================================================================================
 */
	/**
	 * Check if the discount already exists in the food order
	 * @param discountList
	 * @param wanted
	 * @return true if discount is used else false
	 */
	public boolean isDiscountUsed(List<Discount> discountList, Discount wanted) {
		for (Discount discount : discountList) {
			if(discount.getDiscountId() == wanted.getDiscountId()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculates the price of the food order, including the discounts
	 * @param currentFoodList
	 * @param discountList
	 * @return price of the order
	 */
	public double calculatePrice(List<Food> currentFoodList, List<Discount> discountList) {
		double price = 0.0;
		
		for (Food food : currentFoodList) {
			price += food.getPrice();
		}
		
		for (Discount discount : discountList) {
			price = price * (1-discount.getDiscount());
		}
		return Math.round(price * 100.0) / 100.0;
	}
}
