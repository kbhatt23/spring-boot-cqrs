package com.learning.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FailureOrderCommand {

	private String userId;

	private double balance;
	
	private String productId;

	private int quantity;

	@TargetAggregateIdentifier
	private String orderId;
	
	private String shipmentPreference;

}
