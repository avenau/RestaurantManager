package com.avenau.RestaurantManager.controllertests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.avenau.RestaurantManager.controller.HomeController;
import com.avenau.RestaurantManager.models.Food;
import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.DiscountService;
import com.avenau.RestaurantManager.service.FoodService;
import com.avenau.RestaurantManager.service.OrderService;
import com.avenau.RestaurantManager.service.UserService;

public class HomeControllerTests {
	
	private HomeController homeController;

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
		homeController = new HomeController(foodService, userService, orderService, discountService);
	}
	
	@Test
	public void test_getHomePage() {
		assertEquals("home",homeController.getHomePage(session));
	}
	
	@Test
	public void test_getManageFoodPage() {
		assertEquals("manageFood",homeController.getManageFoodPage(model));		
		verify(model).addAttribute("allFoods", foodService.findAll());
	}
	
	@Test
	public void test_getManageDiscountPage() {
		assertEquals("manageDiscounts",homeController.getManageDiscountPage(model));	
		verify(model).addAttribute("allDiscounts", discountService.findAll());
	}
	
	@Test
	public void test_getOrderPage() {
		User mockUser = new User();
		when(session.getAttribute("currentUser")).thenReturn(mockUser);
		when(userService.find(null)).thenReturn(mockUser);
		
		assertEquals("orderPage",homeController.getOrderPage(session,model));	
		verify(model).addAttribute("allFoods", foodService.findAll());
		verify(model).addAttribute("orderPrice", 0.0);
		
	}
	
	@Test
	public void test_getManageOrderPage() {		
		assertEquals("manageOrders",homeController.getManageOrderPage(model));	
		verify(model).addAttribute("allOrders", orderService.findAll());
		
	}

}
