package com.avenau.RestaurantManager.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.DiscountService;
import com.avenau.RestaurantManager.service.FoodService;
import com.avenau.RestaurantManager.service.OrderService;
import com.avenau.RestaurantManager.service.UserService;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	
	private FoodService foodService;
	private UserService userService;
	private OrderService orderService;
	private DiscountService discountService;
	
    protected static final String RETURNING_MAIN_PAGE_MESSAGE = "returning main page";
	private Log log = LogFactory.getLog(HomeController.class);
	
	
	@Autowired
	public HomeController(FoodService service, UserService cService, OrderService oService, DiscountService dService) {
		super();
		this.foodService = service;
		this.userService = cService;
		this.orderService = oService;
		this.discountService = dService;
		
	}
	
	/**
	 * 
	 * @param session
	 * @return home page
	 */

    @GetMapping(value = "/")
    public ResponseEntity<?> mainpage_GET()
    {
        log.info("accessing index route");
        System.out.println("HHelllo!");
        return ResponseEntity.ok(RETURNING_MAIN_PAGE_MESSAGE);
    }
	
	/**
	 * 
	 * @param model
	 * @return Manage Food Page
	 */
/*	@GetMapping("/getManageFoodPage")
	public String getManageFoodPage(Model model) {
		LOGGER.info("Getting Manage Food page!");
		model.addAttribute("allFoods", foodService.findAll());
		LOGGER.debug("allFoods Request Scope: " + model.getAttribute("allFoods"));
		return "manageFood";
	}*/
	
	/**
	 * 
	 * @param model
	 * @return manage discount page
	 */
/*	@GetMapping("/getManageDiscountPage")
	public String getManageDiscountPage(Model model) {
		LOGGER.info("Getting manage discount page!");
		model.addAttribute("allDiscounts", discountService.findAll());
		LOGGER.debug("allDiscounts Request Scope: " + model.getAttribute("allDiscounts"));
		return "manageDiscounts";
	}*/
	
	/**
	 * 
	 * @param session
	 * @param model
	 * @return order page
	 */
/*	@GetMapping("/getOrderPage")
	public String getOrderPage(HttpSession session, Model model) {
		LOGGER.info("Getting Order page!");
		model.addAttribute("orderPrice", 0.0);
		LOGGER.debug("orderPrice Request Scope: " + model.getAttribute("orderPrice"));
		model.addAttribute("allFoods", foodService.findAll());
		LOGGER.debug("allFoods Request Scope: " + model.getAttribute("allFoods"));
		updateUser(session);
		LOGGER.debug("currentUsers Session Scope: " + session.getAttribute(UserController.CURRENT_USER));
		return "orderPage";
	}*/
	
	/**
	 * 
	 * @param model
	 * @return manage order page
	 */
/*	@GetMapping("/getManageOrderPage")
	public String getManageOrderPage( Model model) {
		LOGGER.info("Getting Manage Order page!");
		model.addAttribute("allOrders", orderService.findAll());
		LOGGER.debug("allOrders Request Scope: " + model.getAttribute("allOrders"));
		return "manageOrders";
	}*/
	
	/**
	 * Update the session User object to the user object in the database
	 * @param session
	 */
/*	private void updateUser(HttpSession session) {
		LOGGER.info("Updating user on session!");
		User oldUser = (User) session.getAttribute(UserController.CURRENT_USER);
		session.setAttribute(UserController.CURRENT_USER, userService.find(oldUser.getUser_id()));
		LOGGER.debug("currentUsers Session Scope: " + session.getAttribute(UserController.CURRENT_USER));
	}*/
}
