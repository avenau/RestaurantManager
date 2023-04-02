package com.avenau.RestaurantManager.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Food {
	
	@Id
	@GeneratedValue
	private int food_id;
	private double price;
	private String name;
	
	public Food() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Food(String name, double price) {
		super();
		this.price = price;
		this.name = name;
	}

	public int getFood_id() {
		return food_id;
	}

	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Food [food_id=" + food_id + ", price=" + price + ", name=" + name + "]";
	}
	
	
	
	

}
