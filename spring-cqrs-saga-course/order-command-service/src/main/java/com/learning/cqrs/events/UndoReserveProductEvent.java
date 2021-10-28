package com.learning.cqrs.events;

import lombok.Data;

@Data
public class UndoReserveProductEvent {

	private String productId;
	
	private String userId;
	
	private int quantity;
	
	private String orderId;

}
