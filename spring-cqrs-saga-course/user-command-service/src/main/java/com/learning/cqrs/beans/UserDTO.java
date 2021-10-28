package com.learning.cqrs.beans;

import lombok.Data;

@Data
public class UserDTO {

	private String userId;
	
	private String name;
	
	private double balance;
}
