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
public class UpdateAddressCommand {

	@TargetAggregateIdentifier
	@NotEmpty(message = "accountID can not be blank")
	private String id;

	@NotEmpty(message = "address can not be blank")
	@Size(max = 50,message = "max 50 charachters allowed in address")
	private String address;
	
}
