package com.learning.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UndoReserveProductCommand {

	@TargetAggregateIdentifier
	private String productId;
	
	private String userId;
	
	private int quantity;
	
	private String orderId;
	
}
