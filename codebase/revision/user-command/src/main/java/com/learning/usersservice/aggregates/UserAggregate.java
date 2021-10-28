package com.learning.usersservice.aggregates;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.learning.usersservice.commands.CreateUserCommand;
import com.learning.usersservice.commands.RemoveUserCommand;
import com.learning.usersservice.commands.UpdateUserCommand;
import com.learning.usersservice.config.BcryptPasswordHasher;
import com.learning.usersservice.config.PasswordHasher;
import com.learning.usersservice.events.CreateUserEvent;
import com.learning.usersservice.events.RemoveUserEvent;
import com.learning.usersservice.events.UpdateUserEvent;
import com.learning.usersservice.models.User;

//acting as aggregate
@Aggregate
public class UserAggregate {

	//one aggreaget object have always same id
	//however same aggegate can have any number of events each event having unique id
	@AggregateIdentifier
	private String id;
	
	private User user;
	
	private PasswordHasher passwordHasher;
	
	//no arg constructor
	public UserAggregate() {
		passwordHasher = new BcryptPasswordHasher();
	}

	@CommandHandler
	public UserAggregate(CreateUserCommand createUserCommand) {
		this();
		//copy id to user
		User createUser = createUserCommand.getUser();
		String commandId = createUserCommand.getId();
		createUser.setUserID(commandId);
		String password = createUser.getAccount().getPassword();
		String encodedPassword = passwordHasher.encode(password);
		createUser.getAccount().setPassword(encodedPassword);
		
		//push to event source and event bus
		//remember we must pass the event
		//also rember we update the aggregate object only when we sucesfully pushed data to event source and event bus
		CreateUserEvent createUserEvent = new CreateUserEvent(commandId, createUser); // remember id of each event is unique
		AggregateLifecycle.apply(createUserEvent);
	}
	
	@EventSourcingHandler
	public void on(CreateUserEvent createUserEvent) {
		//we need to update the id of the vent in main aggreagate
		//remember id of each event should be same
		//also we update aggreagte only if event source and event bus were sucesfully pulished with event data
		this.id = createUserEvent.getId();
		this.user=createUserEvent.getUser();
		
	}
	
	//update item
	@CommandHandler
	public void updateUser(UpdateUserCommand updateUserCommand) {
		//again we must do the same
		User updatedUser = updateUserCommand.getUser();
		updatedUser.setUserID(updateUserCommand.getId());
		
		String password = updatedUser.getAccount().getPassword();
		String encodedPassword = passwordHasher.encode(password);
		updatedUser.getAccount().setPassword(encodedPassword);
		
		//create update event and publish to event queue and event source
		//remember we can not use the same id as aggreaget id will be same but event id should be new again and again
		UpdateUserEvent event = new UpdateUserEvent(UUID.randomUUID().toString(), updatedUser) ;// each event should have uniquq id and while create same id as that of aggreagae is already passed
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(UpdateUserEvent updateUserEvent) {
		//no need to update id as for each aggregate id remains same
		this.user=updateUserEvent.getUser();
	}
	
	@CommandHandler
	public void removeUser(RemoveUserCommand removeUserCommand) {
		RemoveUserEvent removeUserEvent = new RemoveUserEvent(removeUserCommand.getId());
		AggregateLifecycle.apply(removeUserEvent);
	}
	
	@EventSourcingHandler
	public void on(RemoveUserEvent removeUserEvent) {
		//freeze the aggregate object and allow for G.C
		AggregateLifecycle.markDeleted();
	}
}
