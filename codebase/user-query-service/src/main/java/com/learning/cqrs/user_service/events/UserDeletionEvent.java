package com.learning.cqrs.user_service.events;

import lombok.Data;

@Data
public class UserDeletionEvent {

	private String id;
}
