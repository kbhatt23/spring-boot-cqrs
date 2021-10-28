package com.learning.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserCommand {
	
	@TargetAggregateIdentifier
	private String userId;
	
	private String name;
	
	private double balance;

}
