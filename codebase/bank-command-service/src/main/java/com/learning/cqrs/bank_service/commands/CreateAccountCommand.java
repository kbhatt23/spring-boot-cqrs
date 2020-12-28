package com.learning.cqrs.bank_service.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.learning.cqrs.bank_service.models.BankAccount;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccountCommand {

	@TargetAggregateIdentifier
	private String id;
	
	@NotNull(message = "Bank account details are mandatory")
	@Valid
	private BankAccount bankAccount;
	
	
}
