package com.learning.cqrs.events;

import lombok.Data;

@Data
public class ReserveProductEvent {

	private String productId;
	
	private String userId;
	
	private int quantity;
	
	private String orderId;

}
