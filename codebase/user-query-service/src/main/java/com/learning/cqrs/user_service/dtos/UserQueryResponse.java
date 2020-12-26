package com.learning.cqrs.user_service.dtos;

import java.util.ArrayList;
import java.util.List;

import com.learning.cqrs.user_service.models.User;

import lombok.Data;

@Data
public class UserQueryResponse {

	private List<User> users;
	
	public UserQueryResponse(User user) {
		if(users == null) {
			users = new ArrayList<User>();
		}
		users.add(user);
	}
	
	public UserQueryResponse(List<User> users) {
		this.users=users;
	}
}
