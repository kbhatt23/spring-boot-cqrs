package com.learning.usersservice.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.learning.usersservice.models.User;

import lombok.Data;

//client will call api by passing thi as body or request
@Data // setter getters, hashcode,equals,tostring
public class CreateUserCommand {

	@NotEmpty(message = "command id can not be empty")
	@TargetAggregateIdentifier
	private String id;
	
	//domain object
	@NotNull(message = "user can not be empty")
	@Valid
	private User user;
}
