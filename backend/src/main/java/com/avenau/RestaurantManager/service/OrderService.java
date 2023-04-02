package com.avenau.RestaurantManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenau.RestaurantManager.dal.OrderRepository;
import com.avenau.RestaurantManager.models.FoodOrder;

@Service
public class OrderService {
	
	private OrderRepository orderRepo;

	@Autowired
	public OrderService(OrderRepository orderRepo) {
		super();
		this.orderRepo = orderRepo;
	}
	
	public FoodOrder find(int id) {
		return orderRepo.getById(id);
	}
	
	public List<FoodOrder> findAll(){
		return orderRepo.findAll();
	}
	
	public FoodOrder save(FoodOrder order) {
		return orderRepo.save(order);
		
	}
	
	public void delete(FoodOrder order) {
		orderRepo.delete(order);
	}
	
	public void deleteAll() {
		orderRepo.deleteAll();
	}
	
	
	
}
