package com.avenau.RestaurantManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenau.RestaurantManager.dal.FoodRepository;
import com.avenau.RestaurantManager.models.Food;

@Service
public class FoodService {
	
	private FoodRepository foodRepo;

	@Autowired
	public FoodService(FoodRepository foodRepo) {
		super();
		this.foodRepo = foodRepo;
	}
	
	public Food save(Food food) {
		return foodRepo.save(food);
	}
	
	public void remove(Food food) {
		foodRepo.delete(food);
	}
	
	public void deleteAll() {
		foodRepo.deleteAll();
	}
	
	public List<Food> findAll(){
		return foodRepo.findAll();
	}
	
	public Food find(int id) {
		Optional<Food> requestedFood = foodRepo.findById(id);
		if (requestedFood.get() == null) {
			return null;
		}
		return requestedFood.get();
	}
}
