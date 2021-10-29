package com.learning.cqrs.events;

import lombok.Data;

@Data
public class UserBalanceDebitEvent {
	
	public static transient final String DEADLINE_NAME = "user-balance-debit-deadline";

	private String userId;

	private double balance;
	
	private String productId;

	private int quantity;

	private String orderId;
}
