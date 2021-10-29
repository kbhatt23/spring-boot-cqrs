package com.learning.cqrs.beans;

import lombok.Data;

@Data
public class Product {
	
	private String productId;

	private String name;
	
	private double price;
	
	private String descripion;
	
	//for stock
	private int quantity;
}
