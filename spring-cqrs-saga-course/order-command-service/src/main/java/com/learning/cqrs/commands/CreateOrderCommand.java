package com.learning.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.learning.cqrs.beans.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {

	@TargetAggregateIdentifier
	private String orderId;
	
	private String productId;
	
	private int quantity;
	
	private String userId;
	
	private OrderStatus orderStatus;
	
	private String shipmentPreference;

}
