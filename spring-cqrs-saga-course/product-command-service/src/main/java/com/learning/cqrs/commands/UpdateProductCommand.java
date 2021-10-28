package com.learning.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductCommand {
	
	@TargetAggregateIdentifier
	private String productId;

	private String name;

	private double price;

	private String descripion;
	
	private int quantity;
}
