package com.learning.usersservice.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//embedded document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	private String userName;
	
	private String password;
	
	private List<Role> roles;
	
}
