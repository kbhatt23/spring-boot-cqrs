package com.learning.bankservice.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // for jackson
public class DepositCommand {

	@TargetAggregateIdentifier
	@NotEmpty(message = "accountID can not be blank")	
	private String id;
	
	@Min(value = 1 , message = "deposit amount should be greater than 1")
	private double amount;
}
