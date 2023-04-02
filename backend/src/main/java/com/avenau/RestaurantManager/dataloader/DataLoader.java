package com.avenau.RestaurantManager.dataloader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.avenau.RestaurantManager.models.Discount;
import com.avenau.RestaurantManager.models.Food;
import com.avenau.RestaurantManager.models.FoodOrder;
import com.avenau.RestaurantManager.models.User;
import com.avenau.RestaurantManager.service.DiscountService;
import com.avenau.RestaurantManager.service.FoodService;
import com.avenau.RestaurantManager.service.OrderService;
import com.avenau.RestaurantManager.service.UserService;

@Component
public class DataLoader implements ApplicationRunner{
	private OrderService orderService;
	private FoodService foodService;
	private UserService customerService;
	private DiscountService discountService;
	
	
	@Autowired
	public DataLoader(OrderService orderService, FoodService foodService, UserService customerService, DiscountService discountService) {
		super();
		this.orderService = orderService;
		this.foodService = foodService;
		this.customerService = customerService;
		this.discountService = discountService;
	}

	@Override
	@Transactional
	@Modifying
	public void run(ApplicationArguments args) throws Exception {
		
		orderService.deleteAll();
		foodService.deleteAll();
		
		Food bigMac = new Food("Big Mac", 6.45);
		Food mcFlurry = new Food("McFlurry", 4.00);
		Food frozenCoke = new Food("Frozen Coke", 1.00);
		Food fries = new Food("Fries", 2.25);
		Food mcSpicy = new Food("McSpicy", 8.70);
		Food coke = new Food("Vanilla Coke", 3.90);
		
		foodService.save(frozenCoke);
		foodService.save(mcFlurry);
		foodService.save(bigMac);
		foodService.save(fries);
		foodService.save(mcSpicy);
		foodService.save(coke);
		
		User aven = new User("AverageJoe", "hi", "Customer");
		User dollaking = new User("ManagerMark", "hi", "Admin");
		customerService.save(aven);
		customerService.save(dollaking);
		
		Discount d1 = new Discount(0.1, "DISCOUNT1111");
		Discount d2 = new Discount(0.01, "DISCOUNT2222");
		discountService.save(d1);
		discountService.save(d2);
		Discount recover = discountService.find("DISCOUNT1111").get();
		
		List<Food> foodList = new ArrayList<Food>();
		foodList.add(bigMac);
		foodList.add(frozenCoke);
		foodList.add(mcFlurry);
		FoodOrder foodOrder = new FoodOrder(dollaking, foodList, new ArrayList<Discount>());
		FoodOrder foodOrder2 = new FoodOrder(aven, foodList, new ArrayList<Discount>());
		orderService.save(foodOrder);
		orderService.save(foodOrder2);
		
		System.out.println(recover);
		
	}
}