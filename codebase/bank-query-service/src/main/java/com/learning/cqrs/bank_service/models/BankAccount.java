package com.learning.cqrs.bank_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "bank_accounts")
public class BankAccount {

	@Id
	private String id;
	
	private Profile profile;

	private Double balance;
	
}
