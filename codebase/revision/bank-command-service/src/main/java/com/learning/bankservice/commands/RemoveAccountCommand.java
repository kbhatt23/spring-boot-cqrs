package com.learning.bankservice.commands;

import javax.validation.constraints.NotEmpty;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveAccountCommand {

	//the id passed here should be the id of aggreagete object
	//if id is sowmthign whihc do not exist then it wont go to aggreagate hadnle method and hence no event wil be passed
	
	@TargetAggregateIdentifier
	@NotEmpty(message = "accountID can not be blank")
	private String id;
}
