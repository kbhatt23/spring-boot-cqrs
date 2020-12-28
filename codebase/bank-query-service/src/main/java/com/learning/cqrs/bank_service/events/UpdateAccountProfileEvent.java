package com.learning.cqrs.bank_service.events;

import com.learning.cqrs.bank_service.models.Profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAccountProfileEvent {

	private String id;
	
	private Profile bankAccount;
	
	
}
