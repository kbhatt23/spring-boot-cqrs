package com.learning.bankservice.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // for jackson
public class CreateAccountCommand {

	//tis id will be aggreagate id
	//goes to constructor and hence we set this id i @eventsorucinghandler method
	//remembers in othereventsorucing ahdlers liek remove or update we do not update the id
	@TargetAggregateIdentifier
	private String id;
	
	@NotEmpty(message = "firstName can not be blank")
	@Size(message = "firstName size should be minimum 5" , min = 5)
	private String firstName;
	
	@NotEmpty(message = "lastName can not be blank")
	@Size(message = "lastName size should be minimum 5", min = 5)
	private String lastName;
	
	//amount can not be negative
	@Min(value = 0, message = "initialAmount can not be negative")
	private double initialAmount;
	
	@NotEmpty(message = "address can not be blank")
	@Size(max = 50,message = "max 50 charachters allowed in address")
	private String address;
}
