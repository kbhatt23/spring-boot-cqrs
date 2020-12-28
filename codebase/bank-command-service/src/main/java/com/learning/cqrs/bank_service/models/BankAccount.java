package com.learning.cqrs.bank_service.models;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	
	@NotNull(message = "Profile details are mandatory")
	@Valid
	private Profile profile;

	//less than 0 balance account not possible
	@Min(value = 0, message = "Balance can not be negative")
	private Double balance;
	
}
