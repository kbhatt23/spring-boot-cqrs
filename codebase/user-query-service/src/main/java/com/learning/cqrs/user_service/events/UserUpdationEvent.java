package com.learning.cqrs.user_service.events;

import com.learning.cqrs.user_service.models.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdationEvent {

	private String id;
	private User user;
}
