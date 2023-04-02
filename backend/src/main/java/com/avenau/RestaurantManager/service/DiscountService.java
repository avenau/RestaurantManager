package com.avenau.RestaurantManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenau.RestaurantManager.dal.DiscountRepository;
import com.avenau.RestaurantManager.models.Discount;

@Service
public class DiscountService {
	
	private DiscountRepository discountRepo;
	
	@Autowired
	public DiscountService(DiscountRepository discountRepo) {
		this.discountRepo = discountRepo;
	}
	
	public Discount save(Discount discount) {
		return discountRepo.save(discount);
	}
	
	public void delete(Discount discount) {
		discountRepo.delete(discount);
	}
	public Optional<Discount> find(int id) {
		return discountRepo.findById(id);
	}
	public Optional<Discount> find(String discountCode) {
		return discountRepo.findByDiscountCode(discountCode);
	}
	public List<Discount> findAll() {
		return discountRepo.findAll();
	}
}
