package com.avenau.RestaurantManager.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	
	@Id
	@GeneratedValue
	private int user_id;
	private String username;
	private String password;
	private String userType;
	@OneToMany(mappedBy = "orderedBy", cascade = CascadeType.ALL)
	private List<FoodOrder> orderList;
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String userType) {
		super();
		this.username = username;
		this.password = password;
		orderList = new ArrayList<FoodOrder>();
		this.userType = userType;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public List<FoodOrder> getOrderList() {
		return orderList;
	}
	
	public void addOrder(FoodOrder order) {
		orderList.add(order);
	}
	
	public void removeOrder(FoodOrder order) {
		orderList.remove(order);
		
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setOrderList(List<FoodOrder> orderList) {
		this.orderList = orderList;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", orderList="
				+ orderList + ", userType=" + userType + "]";
	}
	
	

	
	
}
