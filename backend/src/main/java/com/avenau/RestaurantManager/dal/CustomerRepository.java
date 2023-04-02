package com.avenau.RestaurantManager.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avenau.RestaurantManager.models.User;


@Repository
public interface CustomerRepository  extends JpaRepository<User, Integer>{
	public User findByUsername(String username);
}
