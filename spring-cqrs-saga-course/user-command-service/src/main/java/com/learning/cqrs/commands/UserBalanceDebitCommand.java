package com.learning.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBalanceDebitCommand {

	@TargetAggregateIdentifier
	private String userId;

	private double balance;
	
	private String productId;

	private int quantity;

	private String orderId;
}
