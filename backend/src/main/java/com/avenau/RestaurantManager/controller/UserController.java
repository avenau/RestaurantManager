package com.avenau.RestaurantManager.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.UserService;

@Controller
public class UserController {
	

	//Static constants of jsp pages
	public static final String HOME_URL = "home";
	public static final String LOGIN_URL = "login";
	public static final String REGISTER_URL = "register";
	public static final String CURRENT_USER = "currentUser";
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	
	private UserService userService;
	
	
	@Autowired
	public UserController(UserService service) {
		super();
		this.userService = service;
	}
	
	/**
	 * 
	 * @return register page
	 */
	@GetMapping("/getRegister")
	public String getRegisterPage() {
		LOGGER.info("Getting Register Page!");
		return REGISTER_URL;
	}
	
	/**
	 * 
	 * @return login page
	 */
	@GetMapping("/getLogin")
	public String getLoginPage() {
		LOGGER.info("Getting Login Page!");
		return LOGIN_URL;
	}	
	
	/**
	 * Get user's order history page
	 * @param session
	 * @param model
	 * @return user orders page
	 */
	@GetMapping("/getUserOrdersPage")
	public String getUsersOrdersPage(HttpSession session, Model model) {
		LOGGER.info("Getting User Order Page!");
		User currentCustomer = updateCurrentUser(session);
		model.addAttribute("userOrders",currentCustomer.getOrderList());
		return "userOrders";
	}

	/**
	 * Updates the session with the most recent user object in the database
	 * @param session
	 * @return the most recent user object from the database
	 */
	public User updateCurrentUser(HttpSession session) {
		LOGGER.info("Updating User!");
		User currentCustomer = (User) session.getAttribute(CURRENT_USER);
		User updatedCustomer = userService.find(currentCustomer.getUser_id());
		session.setAttribute(CURRENT_USER, updatedCustomer);
		return updatedCustomer;
	}
	
	
	
	/**
	 * Add user into the session. User getting registered into the system
	 * @return confirm page if successful, if not will return back to register page
	 */
	
	@PostMapping("/registerUser")
	public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirmpassword, HttpSession session, Model model) {
		LOGGER.info("Registering User");
		if (userService.find(username) != null) {
			model.addAttribute("errorMessage", "Username Already Exists!");
			return REGISTER_URL;
		}
		if (!password.equals(confirmpassword)) {
			model.addAttribute("errorMessage", "Passwords DO NOT MATCH!");
			return REGISTER_URL;
		}
		
		User newUser = new User(username, password, "Customer");
		session.setAttribute(CURRENT_USER, newUser);

		this.userService.save(newUser);
		return HOME_URL;
	}	
	
	
	/**
	 * Checks if the user's username and password are correct, if so they are logged in
	 * @param username
	 * @param password
	 * @param session
	 * @param model
	 * @return home page if username and password is valid, if not, will return back to login page
	 */
	@PostMapping("/loginUser")
	public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
		LOGGER.info("Logging User In!");
		if (!userService.validateLogin(username, password)) {
			model.addAttribute("error", "Your username or password is incorrect!");
			return LOGIN_URL;
		}
		User currentUser =userService.find(username);
		session.setAttribute(CURRENT_USER, currentUser);
		return HOME_URL;
	}
	
	/**
	 * Logs user out. Removes user from session
	 * @param session
	 * @return home page
	 */
	@GetMapping("/logoutUser")
	public String logoutUser(HttpSession session) {
		LOGGER.info("Logging User Out!");
		session.invalidate();
		return HOME_URL;
	}
	

}
