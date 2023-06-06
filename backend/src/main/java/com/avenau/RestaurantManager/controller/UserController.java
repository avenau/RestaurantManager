package com.avenau.RestaurantManager.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avenau.RestaurantManager.HttpResponse.LoginResponse;
import com.avenau.RestaurantManager.Security.AccountDetails;
import com.avenau.RestaurantManager.models.AuthRequest;
import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.AccountDetailsService;
import com.avenau.RestaurantManager.service.UserService;
import com.avenau.RestaurantManager.util.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	

	//Static constants of jsp pages
	public static final String HOME_URL = "home";
	public static final String LOGIN_URL = "login";
	public static final String REGISTER_URL = "register";
	public static final String CURRENT_USER = "currentUser";
	private Log log = LogFactory.getLog(AccountDetailsService.class);
	private JwtUtil jwtUtil;
	
	
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
		log.info("Getting Register Page!");
		return REGISTER_URL;
	}
	
	/**
	 * Generates token for front end
	 * @param authRequest
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/auth/login")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		log.info("new login request received");
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Access-Control-Allow-Origin", "*");
		User user = userService.find(authRequest.getUsername());
		AccountDetails accountDetails = new AccountDetails(user);
		String jwt = jwtUtil.generateToken(accountDetails);
		ResponseEntity<LoginResponse> response = ResponseEntity.ok()
			    .header("Access-Control-Allow-Origin", "*")
			    .body(new LoginResponse(HttpStatus.ACCEPTED,
			                             accountDetails.getUsername(),
			                             user.getUser_id(),
			                             ((AccountDetails) accountDetails).getAccountType(),
			                             jwt));
		System.out.println("RESPONSE: " + response);
		return response;
	}
	
/*	@PostMapping("/auth/login")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
	    // your code here
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Access-Control-Allow-Origin", "*");
	    return ResponseEntity.ok()
	            .headers(responseHeaders)
	            .body(new LoginResponse(HttpStatus.ACCEPTED, accountDetails.getUsername(), user.getUser_id(),
	                    ((AccountDetails) accountDetails).getAccountType(), jwt));
	}*/
	
	/**
	 * Get user's order history page
	 * @param session
	 * @param model
	 * @return user orders page
	 */
/*	@GetMapping("/getUserOrdersPage")
	public String getUsersOrdersPage(HttpSession session, Model model) {
		LOGGER.info("Getting User Order Page!");
		User currentCustomer = updateCurrentUser(session);
		model.addAttribute("userOrders",currentCustomer.getOrderList());
		return "userOrders";
	}*/

	/**
	 * Updates the session with the most recent user object in the database
	 * @param session
	 * @return the most recent user object from the database
	 */
/*	public User updateCurrentUser(HttpSession session) {
		LOGGER.info("Updating User!");
		User currentCustomer = (User) session.getAttribute(CURRENT_USER);
		User updatedCustomer = userService.find(currentCustomer.getUser_id());
		session.setAttribute(CURRENT_USER, updatedCustomer);
		return updatedCustomer;
	}*/
	
	
	
	/**
	 * Add user into the session. User getting registered into the system
	 * @return confirm page if successful, if not will return back to register page
	 */
	
/*	@PostMapping("/registerUser")
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
	}	*/
	
	
	/**
	 * Checks if the user's username and password are correct, if so they are logged in
	 * @param username
	 * @param password
	 * @param session
	 * @param model
	 * @return home page if username and password is valid, if not, will return back to login page
	 */
/*	@PostMapping("/loginUser")
	public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
		LOGGER.info("Logging User In!");
		if (!userService.validateLogin(username, password)) {
			model.addAttribute("error", "Your username or password is incorrect!");
			return LOGIN_URL;
		}
		User currentUser =userService.find(username);
		session.setAttribute(CURRENT_USER, currentUser);
		return HOME_URL;
	}*/
	
	/**
	 * Logs user out. Removes user from session
	 * @param session
	 * @return home page
	 */
/*	@GetMapping("/logoutUser")
	public String logoutUser(HttpSession session) {
		LOGGER.info("Logging User Out!");
		session.invalidate();
		return HOME_URL;
	}*/
	

}
