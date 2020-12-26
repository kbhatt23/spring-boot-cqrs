package com.learning.cqrs.user_service.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.learning.cqrs.user_service.commands.UserCreationCommand;
import com.learning.cqrs.user_service.commands.UserDeletionCommand;
import com.learning.cqrs.user_service.commands.UserUpdationCommand;
import com.learning.cqrs.user_service.events.UserCreationEvent;
import com.learning.cqrs.user_service.events.UserDeletionEvent;
import com.learning.cqrs.user_service.events.UserUpdationEvent;
import com.learning.cqrs.user_service.models.User;
import com.learning.cqrs.user_service.security.PasswordEncoder;
import com.learning.cqrs.user_service.security.PasswordEncoderImpl;

import java.util.UUID;

//this is working fine
@Aggregate
public class UserAggregateV1 {
    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    public UserAggregateV1() {
        passwordEncoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregateV1(UserCreationCommand command) {
        User newUser = command.getUser();
        newUser.setId(command.getId());
        String password = newUser.getAccount().getPassword();
        passwordEncoder = new PasswordEncoderImpl();
        String hashedPassword = passwordEncoder.hashPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        UserCreationEvent event = UserCreationEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UserUpdationCommand command) {
        User updatedUser = command.getUser();
        //we must put the command id in controller , so that we can copy from command to user
        updatedUser.setId(command.getId());
        String password = updatedUser.getAccount().getPassword();
        String hashedPassword = passwordEncoder.hashPassword(password);
        updatedUser.getAccount().setPassword(hashedPassword);

        UserUpdationEvent event = UserUpdationEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UserDeletionCommand command) {
    	UserDeletionEvent event = new UserDeletionEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserCreationEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdationEvent event) {
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserDeletionEvent event) {
        AggregateLifecycle.markDeleted();
    }
}

