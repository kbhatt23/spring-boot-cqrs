package com.learning.usersservice.handlers;

import com.learning.usersservice.events.CreateUserEvent;
import com.learning.usersservice.events.RemoveUserEvent;
import com.learning.usersservice.events.UpdateUserEvent;

public interface UserEventHandler {
	public void on(CreateUserEvent createUserEvent);
	public void on(UpdateUserEvent updateUserEvent);
	public void on(RemoveUserEvent removeUserEvent);
}
