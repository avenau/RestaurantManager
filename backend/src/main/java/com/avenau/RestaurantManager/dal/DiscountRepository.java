package com.avenau.RestaurantManager.dal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avenau.RestaurantManager.models.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer>{
	
	public Optional<Discount> findByDiscountCode(String discountCode);

}
