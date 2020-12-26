package com.learning.cqrs.user_service.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cqrs.user_service.events.UserCreationEvent;
import com.learning.cqrs.user_service.events.UserDeletionEvent;
import com.learning.cqrs.user_service.events.UserUpdationEvent;
import com.learning.cqrs.user_service.repositories.UserRepository;

@Service
@ProcessingGroup("user-group")
public class UserEventHandlerImpl implements UserEventHandler {

	@Autowired
	private UserRepository userRepository;
	
	@EventHandler
	@Override
	public void on(UserCreationEvent userCreationEvent) {
		userRepository.save(userCreationEvent.getUser());
	}

	@EventHandler
	@Override
	public void on(UserUpdationEvent userUpdationEvent) {
		userRepository.save(userUpdationEvent.getUser());
	}

	@EventHandler
	@Override
	public void on(UserDeletionEvent userDeletionEvent) {
		userRepository.deleteById(userDeletionEvent.getId());
	}

}
