package com.learning.cqrs.beans;

import lombok.Data;

@Data
public class OrderDTO {

	private String orderId;
	
	private String productId;
	
	private int quantity;
	
	private String userId;
	
	private OrderStatus orderStatus;
	
	private String shipmentPreference;
}
