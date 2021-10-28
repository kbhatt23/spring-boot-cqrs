package com.learning.bankservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // for jackson
public class DepositEvent {

	private String id;
	
	private double amount;
	
	private String accountId;
}
