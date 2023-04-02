package com.avenau.RestaurantManager.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class FoodOrder {
	
	@Id
	@GeneratedValue
	private int order_id;
	@ManyToOne
	private User orderedBy;
	@ManyToMany
	private List<Food> foodList;
	@ManyToMany
	private List<Discount> discountList;
	private String orderStatus;
	private double price;

	public FoodOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FoodOrder(User orderedBy, List<Food> foodList, List<Discount> discountList) {
		super();
		this.orderedBy = orderedBy;
		this.foodList = foodList;
		this.discountList = discountList;
		this.orderStatus = "Preparing";
		this.calculatePrice();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public User getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(User orderedBy) {
		this.orderedBy = orderedBy;
	}

	public List<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	private void calculatePrice() {
		double price = 0.0;
		for (Food food : this.foodList) {
			price += food.getPrice();
		}
		
		for (Discount discount : this.discountList) {
			price = price * (1-discount.getDiscount());
		}
		this.price = Math.round(price * 100.0) / 100.0;
	}	
	
	public List<Discount> getDiscountList() {
		return discountList;
	}

	public void setDiscountList(List<Discount> discountList) {
		this.discountList = discountList;
	}

	@Override
	public String toString() {
		return "FoodOrder [order_id=" + order_id + ", orderedBy=" + orderedBy.getUsername() + ", foodList=" + foodList
				+ ", discountList=" + discountList + ", orderStatus=" + orderStatus + ", price=" + price + "]";
	}
	

	
	
}
