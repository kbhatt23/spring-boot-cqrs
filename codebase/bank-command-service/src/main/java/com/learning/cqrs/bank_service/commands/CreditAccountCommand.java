package com.learning.cqrs.bank_service.commands;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccountCommand {
	@TargetAggregateIdentifier
	private String id;
	
	//in case of profile update we do not update other parts of the model/entity
	private Double amount;
}
