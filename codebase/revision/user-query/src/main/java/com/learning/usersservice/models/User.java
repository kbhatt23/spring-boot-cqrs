package com.learning.usersservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // tostring , hashcode and equals and getters and setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users") // plural collection name
public class User {
	@Id
	private String userID;
	
	private String firstName;
	
	private String lastName;
	
	private Account account;
	
}
