package com.learning.cqrs.events;

import lombok.Data;

@Data
public class CreateUserEvent {
	
	private String userId;
	
	private String name;
	
	private double balance;

}
