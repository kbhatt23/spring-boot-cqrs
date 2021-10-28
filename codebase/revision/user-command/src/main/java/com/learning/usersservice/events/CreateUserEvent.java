package com.learning.usersservice.events;

import com.learning.usersservice.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserEvent {
	private String id;
	
	//domain object
	private User user;

}
