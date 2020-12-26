package com.learning.cqrs.user_service.handlers;

import com.learning.cqrs.user_service.events.UserCreationEvent;
import com.learning.cqrs.user_service.events.UserDeletionEvent;
import com.learning.cqrs.user_service.events.UserUpdationEvent;

public interface UserEventHandler {

	void on(UserCreationEvent userCreationEvent);
	
	void on(UserUpdationEvent userUpdationEvent);
	
	void on(UserDeletionEvent userDeletionEvent);
}
