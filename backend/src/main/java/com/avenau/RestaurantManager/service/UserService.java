package com.avenau.RestaurantManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avenau.RestaurantManager.dal.CustomerRepository;
import com.avenau.RestaurantManager.models.User;

@Service
@Transactional
public class UserService {
	private CustomerRepository userRepo;
	
	@Autowired
	public UserService(CustomerRepository customerRepo) {
		super();
		this.userRepo = customerRepo;
	}
	
	public User save(User customer) {
		User savedCustomer = userRepo.save(customer);
		return savedCustomer;
	}
	
	public void remove(User customer) {
		userRepo.delete(customer);
	}
	
	public User find(int id) {
		Optional<User> foundCustomer = userRepo.findById(id);
		
		if (!foundCustomer.isPresent()) {
			return null;
		}
		
		return (User) foundCustomer.get();
	}
	
	public User find(String name) {
		Optional<User> foundCustomer = userRepo.findByUsername(name);

		if (!foundCustomer.isPresent()) {
			return null;
		}
		
		return foundCustomer.get();
	}
	
	public List<User>findAll() {
		return userRepo.findAll();
	}

	public boolean validateLogin(String username, String password) {
		// TODO Auto-generated method stub
		Optional<User> customer = userRepo.findByUsername(username);
		if (customer == null) {
			System.out.println("first");
			return false;
		}
		
		
		if (!password.equals(customer.get().getPassword())) {
			System.out.println("second");
			return false;
		}
		System.out.println("third");
		
		return true;
	}

	
}
