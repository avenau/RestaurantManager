package com.avenau.RestaurantManager.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Discount {
	
	@Id
	@GeneratedValue
	private int discountId;
	private double discount;
	private String discountCode;
	public Discount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Discount(double discount, String discountCode) {
		super();
		this.discount = discount;
		this.discountCode = discountCode;
	}
	public int getDiscountId() {
		return discountId;
	}
	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
	@Override
	public String toString() {
		return "Discount [discountId=" + discountId + ", discount=" + discount + ", discountCode=" + discountCode + "]";
	}
	
	
}
