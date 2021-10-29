package com.learning.cqrs.events;

import lombok.Data;

@Data
public class ReserveProductEvent {
	public static transient final String DEADLINE_NAME = "reserve-product-deadline";

	private String productId;
	
	private String userId;
	
	private int quantity;
	
	private String orderId;
	

}
