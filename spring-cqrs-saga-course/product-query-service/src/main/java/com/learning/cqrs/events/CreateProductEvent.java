package com.learning.cqrs.events;

import lombok.Data;

@Data
public class CreateProductEvent {

	private String productId;

	private String name;

	private double price;

	private String descripion;
	
	private int quantity;
}
