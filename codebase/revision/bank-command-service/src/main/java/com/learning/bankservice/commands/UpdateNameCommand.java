package com.learning.bankservice.commands;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//we can update name and address here
//rember we are using putmaping means complete object is updated
//whatever comes from postman will be kept as it is
//in patch mapping we merge data
@Data
@AllArgsConstructor
@NoArgsConstructor // for jackson
//put mapping so we must publish and override all data from this contnet completely
public class UpdateNameCommand {

	@TargetAggregateIdentifier
	@NotEmpty(message = "accountID can not be blank")
	private String id;

	@NotEmpty(message = "firstName can not be blank")
	@Size(message = "firstName size should be minimum 5" , min = 5)
	private String firstName;

	@NotEmpty(message = "lastName can not be blank")
	@Size(message = "lastName size should be minimum 5", min = 5)
	private String lastName;
	
}
