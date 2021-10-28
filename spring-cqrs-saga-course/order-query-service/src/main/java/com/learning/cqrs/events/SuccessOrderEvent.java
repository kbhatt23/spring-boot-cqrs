package com.learning.cqrs.events;

import lombok.Data;

@Data
public class SuccessOrderEvent {
	private String userId;
	
	private double balance;
	
	private String productId;
	
	private int quantity;
	
	private String orderId;
	
	private String shipmentPreference;
}

