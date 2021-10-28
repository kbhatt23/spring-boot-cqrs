package com.learning.bankservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//plural and snake case
@Document(collection = "bank_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor

//keeping simple properties for demo
public class BankAccount {

	// account number should be unique for everyone
	@Id
	private String accountNumber;

	private String firstName;

	private String lastName;

	private double amount;

	private String address;
	
	public void deposit(double amount) {
		this.amount +=amount;
	}
	
	public void withdraw(double amount) {
		this.amount -=amount;
	}
}
