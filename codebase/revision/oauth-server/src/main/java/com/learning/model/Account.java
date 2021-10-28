package com.learning.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//embedded document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Size(message = "userName must be of size 5")
	private String userName;
	
	@Size(message = "userName must be of size 7")
	private String password;
	
	@NotNull(message = "minimum 1 role is needed")
	private List<Role> roles;
	
}
