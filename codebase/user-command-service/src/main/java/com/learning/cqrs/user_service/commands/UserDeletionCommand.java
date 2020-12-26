package com.learning.cqrs.user_service.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class UserDeletionCommand {
	
	@TargetAggregateIdentifier
	private String id;
}
