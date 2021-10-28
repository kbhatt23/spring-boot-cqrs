package com.learning.usersservice.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.usersservice.events.CreateUserEvent;
import com.learning.usersservice.events.RemoveUserEvent;
import com.learning.usersservice.events.UpdateUserEvent;
import com.learning.usersservice.models.User;
import com.learning.usersservice.repositories.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@ProcessingGroup("user-group")
@Slf4j
public class UserEventHandlerImpl implements UserEventHandler{

	@Autowired
	private UsersRepository usersRepository;
	
	@EventHandler
	@Override
	public void on(CreateUserEvent createUserEvent) {
		User user = createUserEvent.getUser();
		if(user != null && !usersRepository.existsById(user.getUserID())) {
			//user is not null and also it do not exist in D.B
			usersRepository.save(user);
			log.error("user created for event "+createUserEvent);
		}else {
			log.error("user not created for event "+createUserEvent);
		}
	}
	
	@EventHandler
	@Override
	public void on(UpdateUserEvent updateUserEvent) {
		User user = updateUserEvent.getUser();
		if(user != null && usersRepository.existsById(user.getUserID())) {
			//user is not null and also it must exist in D.B
			usersRepository.save(user);
			log.error("user updated for event "+updateUserEvent);
		}else {
			log.error("user not updated for event "+updateUserEvent);
		}
	}

	@EventHandler
	@Override
	public void on(RemoveUserEvent removeUserEvent) {
		usersRepository.deleteById(removeUserEvent.getId());
		log.error("user removed for event "+removeUserEvent);
	}

}
