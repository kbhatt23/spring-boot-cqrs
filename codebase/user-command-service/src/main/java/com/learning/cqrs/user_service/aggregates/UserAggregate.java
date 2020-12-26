//package com.learning.cqrs.user_service.aggregates;
//
//import java.util.UUID;
//
//import org.axonframework.commandhandling.CommandHandler;
//import org.axonframework.eventsourcing.EventSourcingHandler;
//import org.axonframework.modelling.command.AggregateLifecycle;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;
//import org.axonframework.spring.stereotype.Aggregate;
//
//import com.learning.cqrs.user_service.commands.UserCreationCommand;
//import com.learning.cqrs.user_service.commands.UserDeletionCommand;
//import com.learning.cqrs.user_service.commands.UserUpdationCommand;
//import com.learning.cqrs.user_service.events.UserCreationEvent;
//import com.learning.cqrs.user_service.events.UserDeletionEvent;
//import com.learning.cqrs.user_service.events.UserUpdationEvent;
//import com.learning.cqrs.user_service.models.User;
//import com.learning.cqrs.user_service.security.PasswordEncoder;
//import com.learning.cqrs.user_service.security.PasswordEncoderImpl;
//
//
//@Aggregate
//public class UserAggregate {
//
//	@TargetAggregateIdentifier
//	private String id;
//	
//	//model -> document
//	private User user;
//	
//	private final PasswordEncoder passwordEncoder;
//	
//	public UserAggregate() {
//		this.passwordEncoder=new PasswordEncoderImpl();
//	}
//
//	//creation command -> can be handled by constructor
//	@CommandHandler
//	public UserAggregate(UserCreationCommand userCreationCommand) {
//		this();
//		User newUser = userCreationCommand.getUser();
//		newUser.setId(userCreationCommand.getId());
//		String password = newUser.getAccount().getPassword();
//		String hashPassword = passwordEncoder.hashPassword(password);
//		newUser.getAccount().setPassword(hashPassword);
//		UserCreationEvent userCreationEvent = UserCreationEvent.builder()
//						 .id(userCreationCommand.getId())
//						 .user(newUser)
//						 .build();
//		AggregateLifecycle.apply(userCreationEvent);
//		
//	}
//	
//	//update and delete can be methods 
//	@CommandHandler
//	public void handle(UserDeletionCommand userDeletionCommand) {
//		UserDeletionEvent userDeletionEvent = new UserDeletionEvent();
//		userDeletionEvent.setId(userDeletionCommand.getId());
//		AggregateLifecycle.apply(userDeletionEvent);
//	}
//	@CommandHandler
//	public void handle(UserUpdationCommand userUpdationCommand) {
//		User userUpdate = userUpdationCommand.getUser();
//		userUpdate.setId(userUpdationCommand.getId());
//		String password = userUpdate.getAccount().getPassword();
//		String hashPassword = passwordEncoder.hashPassword(password);
//		userUpdate.getAccount().setPassword(hashPassword);
//		
//		UserUpdationEvent userUpdationEvent = UserUpdationEvent.builder()
//				 .id(UUID.randomUUID().toString())
//				 .user(userUpdate)
//				 .build();
//		AggregateLifecycle.apply(userUpdationEvent);
//	}
//	
//	@EventSourcingHandler
//	public void on(UserCreationEvent userCreationEvent) {
//		this.id = userCreationEvent.getId();
//		this.user = userCreationEvent.getUser();
//	}
//	
//	@EventSourcingHandler
//	public void on(UserDeletionEvent userDeletionEvent) {
//		AggregateLifecycle.markDeleted();
//	}
//	
//	@EventSourcingHandler
//	public void on(UserUpdationEvent userUpdationEvent) {
//		this.id=userUpdationEvent.getId();
//		this.user=userUpdationEvent.getUser();
//	}
//	
//}
