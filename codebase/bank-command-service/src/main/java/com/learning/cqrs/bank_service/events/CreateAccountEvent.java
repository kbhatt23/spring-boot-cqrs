package com.learning.cqrs.bank_service.events;

import com.learning.cqrs.bank_service.models.BankAccount;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CreateAccountEvent {
	private String id;
	
	private BankAccount bankAccount;
}
