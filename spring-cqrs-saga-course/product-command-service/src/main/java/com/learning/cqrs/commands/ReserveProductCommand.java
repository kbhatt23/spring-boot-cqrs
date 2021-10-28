package com.learning.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveProductCommand {

	@TargetAggregateIdentifier
	private String productId;
	
	private String userId;
	
	private int quantity;
	
	private String orderId;
	
}
