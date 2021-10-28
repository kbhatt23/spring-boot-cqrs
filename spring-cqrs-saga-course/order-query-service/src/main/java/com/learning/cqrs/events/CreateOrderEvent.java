package com.learning.cqrs.events;

import com.learning.cqrs.beans.OrderStatus;

import lombok.Data;
@Data
public class CreateOrderEvent {
	private String orderId;

	private String productId;

	private int quantity;

	private String userId;

	private OrderStatus orderStatus;
	
	private String shipmentPreference;
}
