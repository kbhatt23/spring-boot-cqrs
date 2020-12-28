package com.learning.cqrs.oauth_server.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

	private String userName;
	
	private String password;
	
	private List<Role> roles;
}
