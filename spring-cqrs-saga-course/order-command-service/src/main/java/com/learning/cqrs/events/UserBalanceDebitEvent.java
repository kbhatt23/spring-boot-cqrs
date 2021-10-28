package com.learning.cqrs.events;

import lombok.Data;

@Data
public class UserBalanceDebitEvent {

	private String userId;

	private double balance;
	
	private String productId;

	private int quantity;

	private String orderId;
}
