package com.avenau.RestaurantManager.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avenau.RestaurantManager.models.FoodOrder;


@Repository
public interface OrderRepository extends JpaRepository<FoodOrder, Integer>{

}
