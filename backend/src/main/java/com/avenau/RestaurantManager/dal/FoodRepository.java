package com.avenau.RestaurantManager.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avenau.RestaurantManager.models.Food;


@Repository
public interface FoodRepository extends JpaRepository<Food, Integer>{

}
