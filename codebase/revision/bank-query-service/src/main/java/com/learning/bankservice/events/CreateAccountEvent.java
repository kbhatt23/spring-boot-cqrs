package com.learning.bankservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // for jackson
public class CreateAccountEvent {

	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private double initialAmount;
	
	private String address;
}
