package com.learning.cqrs.eventhandlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.learning.cqrs.entities.UserEntity;
import com.learning.cqrs.entities.UserRepository;
import com.learning.cqrs.events.CreateUserEvent;
import com.learning.cqrs.events.UserBalanceCreditEvent;
import com.learning.cqrs.events.UserBalanceDebitEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ProcessingGroup("user-service-group")
public class UserEventHandler {

	@Autowired
	private UserRepository userRepository;
	
	@EventHandler
	public void on(CreateUserEvent createUserEvent) {

		log.info("on: recieved createUserEvent: "+createUserEvent);
		String userId = createUserEvent.getUserId();
		if(StringUtils.isEmpty(userId) || userRepository.existsById(userId)) {
			throw new IllegalArgumentException("createUserEvent: invalid userId passed "+userId);
		}
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(createUserEvent, entity);
		userRepository.save(entity);
	
	}
	
	@EventHandler
	public void on(UserBalanceDebitEvent userBalanceDebitEvent) {
		log.info("on: recieved userBalanceDebitEvent: "+userBalanceDebitEvent);
		String userId = userBalanceDebitEvent.getUserId();
		if(StringUtils.isEmpty(userId) || !userRepository.existsById(userId)) {
			throw new IllegalArgumentException("userBalanceDebitEvent: invalid userId passed "+userId);
		}

		userRepository.findById(userId)
		.map(user ->{ user.setBalance(user.getBalance() - userBalanceDebitEvent.getBalance()); return user;})
		.map(userRepository :: save)
		.orElseThrow(() ->  new IllegalArgumentException("userBalanceDebitEvent: invalid userId passed "+userId))
		;
	}
	
	@EventHandler
	public void on(UserBalanceCreditEvent userBalanceCreditEvent) {
		log.info("on: recieved userBalanceCreditEvent: "+userBalanceCreditEvent);
		String userId = userBalanceCreditEvent.getUserId();
		if(StringUtils.isEmpty(userId) || !userRepository.existsById(userId)) {
			throw new IllegalArgumentException("userBalanceCreditEvent: invalid userId passed "+userId);
		}

		userRepository.findById(userId)
		.map(user ->{ user.setBalance(user.getBalance() + userBalanceCreditEvent.getBalance()); return user;})
		.map(userRepository :: save)
		.orElseThrow(() ->  new IllegalArgumentException("userBalanceCreditEvent: invalid userId passed "+userId))
		;
	}
}
