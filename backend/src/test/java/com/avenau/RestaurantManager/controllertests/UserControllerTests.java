package com.avenau.RestaurantManager.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;

import com.avenau.RestaurantManager.controller.UserController;
import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.DiscountService;
import com.avenau.RestaurantManager.service.FoodService;
import com.avenau.RestaurantManager.service.OrderService;
import com.avenau.RestaurantManager.service.UserService;

public class UserControllerTests {
	private UserController userController;

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
		userController = new UserController(userService);
	}
	
	@Test
	public void test_getRegisterPage() {
		assertEquals("register",userController.getRegisterPage());
	}
	
	@Test
	public void test_getLoginPage() {
		assertEquals(UserController.LOGIN_URL,userController.getLoginPage());
	}
	
	@Test
	public void test_getUserOrdersPage() {
		User user = new User();
		when(session.getAttribute(UserController.CURRENT_USER)).thenReturn(user);
		when(userService.find(user.getUser_id())).thenReturn(user);	
		assertEquals("userOrders",userController.getUsersOrdersPage(session, model));
		verify(model, times(1)).addAttribute("userOrders", user.getOrderList());
	}
	
	@Test
	public void test_updateCurrentUser() {
		User user = new User();
		when(session.getAttribute(UserController.CURRENT_USER)).thenReturn(user);
		when(userService.find(user.getUser_id())).thenReturn(user);	
		
		assertEquals(user, userController.updateCurrentUser(session));
		verify(session, times(1)).setAttribute(UserController.CURRENT_USER, user);		
	}
	
	@Test
	public void test_registerUser_user_already_exist() {
		String username = "username";
		String password = "password";
		String comPass = "password";
		
		when(userService.find(username)).thenReturn(new User());
		
		assertEquals(userController.registerUser(username, password, comPass, session, model), userController.REGISTER_URL);
		verify(model, times(1)).addAttribute("errorMessage", "Username Already Exists!");	
	}
	
	@Test
	public void test_registerUser_bad_password() {
		String username = "username";
		String password = "password";
		String comPass = "4password";
		
		when(userService.find(username)).thenReturn(null);
		
		assertEquals(userController.registerUser(username, password, comPass, session, model), userController.REGISTER_URL);
		verify(model, times(1)).addAttribute("errorMessage", "Passwords DO NOT MATCH!");	
	}
	
	@Test
	public void test_registerUser_success() {
		String username = "username";
		String password = "password";
		String comPass = "password";
		
		when(userService.find(username)).thenReturn(null);
		
		assertEquals(userController.registerUser(username, password, comPass, session, model), userController.HOME_URL);

	}
	
	@Test
	public void test_loginUser_fail() {
		String username = "username";
		String password = "password";
		
		when(userService.validateLogin(username, password)).thenReturn(false);
		
		assertEquals(userController.loginUser(username, password, session, model), userController.LOGIN_URL);
		verify(model, times(1)).addAttribute("error", "Your username or password is incorrect!");

	}
	
	@Test
	public void test_loginUser_sucess() {
		String username = "username";
		String password = "password";
		
		when(userService.validateLogin(username, password)).thenReturn(true);
		
		assertEquals(userController.loginUser(username, password, session, model), userController.HOME_URL);
	}
	
	@Test
	public void test_logout() {		
		
		assertEquals(userController.logoutUser(session), userController.HOME_URL);
		verify(session, times(1)).invalidate();
	}


}
