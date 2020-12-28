package com.learning.cqrs.bank_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditAccountEvent {
	private String id;
	
	private Double amount;
}
