package com.learning.usersservice.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	//will get added in aggregate
	@Id
	private String userID;
	
	@NotEmpty(message = "firstName can not be empty")
	private String firstName;
	
	@NotEmpty(message = "lastName can not be empty")
	private String lastName;
	
	@NotNull(message = "account can not be empty")
	@Valid
	private Account account;
	
}
