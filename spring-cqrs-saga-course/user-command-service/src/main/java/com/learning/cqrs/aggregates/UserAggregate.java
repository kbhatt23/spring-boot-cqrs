package com.learning.cqrs.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.learning.cqrs.commands.CreateUserCommand;
import com.learning.cqrs.commands.UserBalanceCreditCommand;
import com.learning.cqrs.commands.UserBalanceDebitCommand;
import com.learning.cqrs.events.CreateUserEvent;
import com.learning.cqrs.events.UserBalanceCreditEvent;
import com.learning.cqrs.events.UserBalanceDebitEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class UserAggregate {

	@AggregateIdentifier
	private String userId;
	
	private String name;
	
	private double balance;
	
	public UserAggregate() {
		
	}
	
	@CommandHandler
	public UserAggregate(CreateUserCommand createUserCommand) {
		log.info("createUserCommand called with data: "+createUserCommand);
		
		if(StringUtils.isEmpty(createUserCommand.getUserId())) {
			throw new IllegalArgumentException("createUserCommand: userId can not be empty while creating user");
		}
		
		if(createUserCommand.getBalance() <= 0) {
			throw new IllegalArgumentException("createUserCommand: balance can not be zero while creating user");
		}
		
		CreateUserEvent createUserEvent = new CreateUserEvent();
		BeanUtils.copyProperties(createUserCommand, createUserEvent);
		
		AggregateLifecycle.apply(createUserEvent);
	}
	
	@EventSourcingHandler
	public void on(CreateUserEvent createUserEvent) {
		log.info("createUserEvent called with data: "+createUserEvent);
		
		this.userId=createUserEvent.getUserId();
		this.name=createUserEvent.getName();
		this.balance = createUserEvent.getBalance();
	}
	
	@CommandHandler
	public void handle(UserBalanceDebitCommand userBalanceDebitCommand) {
		log.info("userBalanceDebitCommand called with data: "+userBalanceDebitCommand);
		
		if(StringUtils.isEmpty(userBalanceDebitCommand.getUserId())) {
			throw new IllegalArgumentException("userBalanceDebitCommand: userId can not be empty while creating user");
		}
		
		if(balance < userBalanceDebitCommand.getBalance()) {
			throw new IllegalArgumentException("userBalanceDebitCommand: not enough available balance");
		}
		
		UserBalanceDebitEvent userBalanceDebitEvent = new UserBalanceDebitEvent();
		BeanUtils.copyProperties(userBalanceDebitCommand, userBalanceDebitEvent);
		
		AggregateLifecycle.apply(userBalanceDebitEvent);
		
	}
	
	@EventSourcingHandler
	public void on(UserBalanceDebitEvent userBalanceDebitEvent) {
		log.info("userBalanceDebitEvent called with data: "+userBalanceDebitEvent);
		
		this.balance -= userBalanceDebitEvent.getBalance();
	}

	
	@CommandHandler
	public void handle(UserBalanceCreditCommand userBalanceCreditCommand) {
		log.info("userBalanceCreditCommand called with data: "+userBalanceCreditCommand);
		
		if(StringUtils.isEmpty(userBalanceCreditCommand.getUserId())) {
			throw new IllegalArgumentException("userBalanceCreditCommand: userId can not be empty while creating user");
		}
		
		if(balance < userBalanceCreditCommand.getBalance()) {
			throw new IllegalArgumentException("userBalanceCreditCommand: not enough available balance");
		}
		
		UserBalanceCreditEvent userBalanceCreditEvent = new UserBalanceCreditEvent();
		BeanUtils.copyProperties(userBalanceCreditCommand, userBalanceCreditEvent);
		
		AggregateLifecycle.apply(userBalanceCreditEvent);
		
	}
	
	@EventSourcingHandler
	public void on(UserBalanceCreditEvent userBalanceCreditEvent) {
		log.info("userBalanceCreditEvent called with data: "+userBalanceCreditEvent);
		
		this.balance += userBalanceCreditEvent.getBalance();
	}

}
