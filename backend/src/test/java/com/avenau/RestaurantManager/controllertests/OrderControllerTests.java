package com.avenau.RestaurantManager.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.springframework.ui.Model;

import com.avenau.RestaurantManager.controller.OrderController;
import com.avenau.RestaurantManager.controller.UserController;
import com.avenau.RestaurantManager.models.Discount;
import com.avenau.RestaurantManager.models.Food;
import com.avenau.RestaurantManager.models.FoodOrder;
import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.DiscountService;
import com.avenau.RestaurantManager.service.FoodService;
import com.avenau.RestaurantManager.service.OrderService;
import com.avenau.RestaurantManager.service.UserService;

public class OrderControllerTests {

	private OrderController orderController;

	@Mock
	private FoodService foodService;
	
	@Mock
	private DiscountService discountService;
	
	@Mock
	private OrderService orderService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private HttpSession session;
	
	@Mock
	private Model model;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		orderController = new OrderController(userService, discountService, foodService, orderService);
	}
	
	@Test
	public void test_isDiscountUsed_true() {
		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount(0.5, "FIFTY");
		discountList.add(discount);
		assertTrue(orderController.isDiscountUsed(discountList, discount));	
	}
	
	@Test
	public void test_isDiscountUsed_false() {
		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount(0.5, "FIFTY");

		assertFalse(orderController.isDiscountUsed(discountList, discount));	
	}
	
	@Test
	public void test_calculatePrice() {
		List<Discount> discountList = new ArrayList<Discount>();
		Discount discount = new Discount(0.5, "FIFTY");
		List<Food> foodList = new ArrayList<Food>();
		Food food = new Food("Big Mac", 4.0);
		
		discountList.add(discount);
		foodList.add(food);

		assertEquals(2.0, orderController.calculatePrice(foodList, discountList));	
	}
	
	@Test
	public void test_addDiscount_no_discount_code() {
		String discountCode ="";
		
		assertEquals("manageDiscounts", orderController.addDiscount(discountCode, "0.5", model));
		verify(model, times(1)).addAttribute("errorMessage", "Please Enter Valid Inputs!");
	}
	
	@Test
	public void test_addDiscount_bad_discount() {
		String discountCode ="hello";
		String discount = "invalid";
		
		assertEquals("manageDiscounts", orderController.addDiscount(discountCode, discount, model));
		verify(model, times(1)).addAttribute("errorMessage", "Please Enter Valid Inputs!");
	}
	
	@Test
	public void test_addDiscount_success() {
		String discountCode ="hello";
		String discount = "0.5";
		
		assertEquals("manageDiscounts", orderController.addDiscount(discountCode, discount, model));
	}
	
	@Test
	public void test_deleteDiscount() {
		int discountId = 2;
		
		assertEquals("manageDiscounts", orderController.deleteDiscount(discountId, session, model));
	}
	
	@Test
	public void test_addDiscountToOrder_discount_not_present() {
		String discountCode = "hello";
		Optional<Discount> optional = Optional.empty();
		
		when(discountService.find(discountCode)).thenReturn(optional);
		
		assertEquals("orderPage", orderController.addDiscountToOrder(discountCode,session, model));
		verify(model, times(1)).addAttribute("orderPrice", model.getAttribute("orderPrice"));
		verify(model, times(1)).addAttribute("errorMessage", "Input a valid Discount");
	}
	
	@Test
	public void test_addDiscountToOrder_discount_already_used() {
		String discountCode ="hello";
		Discount currentDiscount = new Discount(0.5,discountCode);
		Optional<Discount> optional = Optional.of(currentDiscount);
		List<Discount>discountList = new ArrayList<Discount>();
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();
		foodList.add(food);
		discountList.add(currentDiscount);
		

		when(discountService.find(discountCode)).thenReturn(optional);

		when(session.getAttribute("discounts")).thenReturn(discountList);
		
		assertEquals("orderPage", orderController.addDiscountToOrder(discountCode,session, model));
		verify(model).addAttribute("orderPrice", model.getAttribute("orderPrice"));
		verify(model).addAttribute("allFoods", foodService.findAll());
		verify(model).addAttribute("errorMessage", "You already used this discount in this order!");
	}
	
	@Test
	public void test_addDiscountToOrder_discount_sucess() {
		String discountCode ="hello";
		Discount currentDiscount = new Discount(0.5,discountCode);
		Optional<Discount> optional = Optional.of(currentDiscount);
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();
		foodList.add(food);
		

		when(discountService.find(discountCode)).thenReturn(optional);

		when(session.getAttribute("order")).thenReturn(foodList);
		
		assertEquals("orderPage", orderController.addDiscountToOrder(discountCode,session, model));
		verify(model).addAttribute("orderPrice", 3.23);
		verify(model).addAttribute("allFoods", foodService.findAll());
	}
	
	@Test
	public void test_addToOrder_sucess() {
		String discountCode ="hello";
		int foodId = 3;
		Discount currentDiscount = new Discount(0.5,discountCode);
		Optional<Discount> optional = Optional.of(currentDiscount);
		List<Discount>discountList = new ArrayList<Discount>();
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();		
	
		discountList.add(currentDiscount);
		
		when(discountService.find(discountCode)).thenReturn(optional);
		when(session.getAttribute("order")).thenReturn(foodList);
		when(foodService.find(foodId)).thenReturn(food);
		when(session.getAttribute("discounts")).thenReturn(discountList);
		
		assertEquals("orderPage", orderController.addToOrder(foodId,session, model));
		verify(model).addAttribute("orderPrice", 3.23);
		verify(model).addAttribute("allFoods", foodService.findAll());
	}
	
	@Test
	public void test_completeOrder_empty() {
		
		when(session.getAttribute("order")).thenReturn(null);
		
		assertEquals("orderPage", orderController.completeOrder(session, model));
		verify(model, times(1)).addAttribute("orderPrice", 0.0);
		verify(model, times(1)).addAttribute("allFoods", foodService.findAll());
		verify(model, times(1)).addAttribute("errorMessage", "Please add a food item!");
	}
	
	@Test
	public void test_completeOrder_success() {
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();
		foodList.add(food);
		
		when(session.getAttribute("order")).thenReturn(foodList);
		when(session.getAttribute(UserController.CURRENT_USER)).thenReturn(new User("hello", "hello", "Customer"));
		
		assertEquals("orderConfirm", orderController.completeOrder(session, model));
		verify(session, times(1)).removeAttribute("order");
		verify(session, times(1)).removeAttribute("discounts");
	}
	
	@Test
	public void test_clearOrder() {
		
		assertEquals("orderPage", orderController.clearOrder(session, model));
		verify(session, times(1)).removeAttribute("order");
		verify(session, times(1)).removeAttribute("discounts");
		verify(model, times(1)).addAttribute("allFoods", foodService.findAll());
		verify(model, times(1)).addAttribute("orderPrice", 0.0);
	}
	
	@Test
	public void test_orderStatus() {
		int orderId = 3;
		String orderStatus = "Ready";
		String discountCode ="hello";
		Discount currentDiscount = new Discount(0.5,discountCode);
		List<Discount>discountList = new ArrayList<Discount>();
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();
		foodList.add(food);
		discountList.add(currentDiscount);
		FoodOrder foodOrder = new FoodOrder(new User("HELLO", "hi", "Customer"),foodList, discountList );
		
		when(orderService.find(orderId)).thenReturn(foodOrder);
		
		assertEquals("manageOrders", orderController.changeOrderStatus(orderId, orderStatus,session, model));
		verify(orderService, times(1)).save(foodOrder);
		verify(model, times(1)).addAttribute("allOrders", orderService.findAll());
	}
	
	@Test
	public void test_deleteOrder() {
		int orderId = 3;
		String discountCode ="hello";
		Discount currentDiscount = new Discount(0.5,discountCode);
		List<Discount>discountList = new ArrayList<Discount>();
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();
		foodList.add(food);
		discountList.add(currentDiscount);
		FoodOrder foodOrder = new FoodOrder(new User("HELLO", "hi", "Customer"),foodList, discountList );
		
		when(orderService.find(orderId)).thenReturn(foodOrder);
		
		assertEquals("manageOrders", orderController.deleteOrder(orderId,session, model));
		verify(orderService, times(1)).delete(foodOrder);
		verify(model, times(1)).addAttribute("allOrders", orderService.findAll());
	}
	
	@Test
	public void test_deleteMyOrder() {
		User newUser = new User("HELLO", "hi", "Customer");
		int orderId = 3;
		String discountCode ="hello";
		Discount currentDiscount = new Discount(0.5,discountCode);
		List<Discount>discountList = new ArrayList<Discount>();
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();
		foodList.add(food);
		discountList.add(currentDiscount);
		FoodOrder foodOrder = new FoodOrder(newUser,foodList, discountList );
		
		when(orderService.find(orderId)).thenReturn(foodOrder);
		when(userService.find(foodOrder.getOrderedBy().getUser_id())).thenReturn(newUser);
		
		assertEquals("userOrders", orderController.deleteMyOrder(orderId,session, model));
		verify(orderService, times(1)).delete(foodOrder);
		verify(model, times(1)).addAttribute("userOrders", newUser.getOrderList());
		verify(session, times(1)).setAttribute(UserController.CURRENT_USER, newUser);
	}
	
	@Test
	public void test_deleteFood() {
		int foodId = 3;
		String discountCode ="hello";
		Discount currentDiscount = new Discount(0.5,discountCode);
		List<Discount>discountList = new ArrayList<Discount>();
		Food food = new Food("Big Mac", 6.45);
		List<Food>foodList = new ArrayList<Food>();
		foodList.add(food);
		discountList.add(currentDiscount);
		when(foodService.find(foodId)).thenReturn(food);
		
		assertEquals("manageFood", orderController.deleteFood(foodId, model));
		verify(foodService, times(1)).remove(food);
		verify(model, times(1)).addAttribute("allFoods", foodService.findAll());
	}
	
	@Test
	public void test_addeFood_no_name() {
		String name = "";
		String price = "3.0";
		
		assertEquals("manageFood", orderController.addFood(name, price, model));
		verify(model, times(1)).addAttribute("errorMessage", "Please Enter Valid Inputs!");
		verify(model, times(1)).addAttribute("allFoods", foodService.findAll());
	}
	
	@Test
	public void test_addeFood_bad_price() {
		String name = "McFlurry";
		String price = "3.0dssf";
		
		assertEquals("manageFood", orderController.addFood(name, price, model));
		verify(model, times(1)).addAttribute("errorMessage", "Please Enter Valid Inputs!");
		verify(model, times(1)).addAttribute("allFoods", foodService.findAll());
	}
	
	@Test
	public void test_addeFood_success() {
		String name = "McFlurry";
		String price = "3.0";
		
		assertEquals("manageFood", orderController.addFood(name, price, model));
		verify(model, times(1)).addAttribute("allFoods", foodService.findAll());
	}
}
