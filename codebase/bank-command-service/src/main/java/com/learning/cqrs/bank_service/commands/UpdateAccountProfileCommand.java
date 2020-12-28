package com.learning.cqrs.bank_service.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.learning.cqrs.bank_service.models.Profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAccountProfileCommand {

	@TargetAggregateIdentifier
	private String id;
	
	//in case of profile update we do not update other parts of the model/entity
	private Profile profile;
	
	
}
