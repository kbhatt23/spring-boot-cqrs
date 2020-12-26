package com.learning.cqrs.user_service.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.learning.cqrs.user_service.models.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreationCommand {

	@TargetAggregateIdentifier
	private String id;
	
	@Valid
	@NotNull(message = "Please provide user details")
	private User user;
}
