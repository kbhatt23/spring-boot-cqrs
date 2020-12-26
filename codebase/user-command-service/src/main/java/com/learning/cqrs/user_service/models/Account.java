package com.learning.cqrs.user_service.models;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

	@Size(min = 7,message = "Username must be of minimum size 7")
	private String userName;
	
	@Size(min = 7,message = "Password must be of minimum size 7")
	private String password;
	
	@NotNull(message = "Atleast one role should be specified")
	private List<Role> roles;
}
