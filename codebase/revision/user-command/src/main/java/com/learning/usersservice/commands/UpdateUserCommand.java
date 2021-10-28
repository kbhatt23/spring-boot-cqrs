package com.learning.usersservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.learning.usersservice.models.User;

import lombok.Data;
//client will call api endpoint by passing this object as json body
@Data
public class UpdateUserCommand {

	@TargetAggregateIdentifier
	private String id;

	
	private User user;
}

