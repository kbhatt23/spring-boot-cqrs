package com.learning.usersservice.controllers;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.usersservice.commands.CreateUserCommand;
import com.learning.usersservice.commands.RemoveUserCommand;
import com.learning.usersservice.commands.UpdateUserCommand;
import com.learning.usersservice.dtos.CommandResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {

	
	@Autowired
	private CommandGateway commandGateway;
	
	@PostMapping
	@PreAuthorize("#oauth2.hasScope('write')")
	public ResponseEntity<CommandResponse> createUser(@Valid @RequestBody CreateUserCommand createUserCommand){
		
		try {
			commandGateway.send(createUserCommand); // non blocking
			log.debug("createUser: User {} created Succesfuly",createUserCommand.getUser());
			return new ResponseEntity<CommandResponse>(new CommandResponse("User created Succesfuly"),HttpStatus.CREATED );
		}catch (Exception e) {
			log.error("createUser: Exception occurred "+e);
			return new ResponseEntity<CommandResponse>(new CommandResponse("User can not be created"),HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
		
	}
	
	@PutMapping
	@PreAuthorize("#oauth2.hasScope('write')")
	public ResponseEntity<CommandResponse> updateUser(@Valid @RequestBody UpdateUserCommand updateUserCommand){
		try {
			commandGateway.send(updateUserCommand); // non blocking
			log.debug("createUser: User {} created Succesfuly",updateUserCommand.getUser());
			return new ResponseEntity<CommandResponse>(new CommandResponse("User updated Succesfuly"),HttpStatus.CREATED );
		}catch (Exception e) {
			log.error("createUser: Exception occurred "+e);
			return new ResponseEntity<CommandResponse>(new CommandResponse("User can not be updated"),HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@DeleteMapping("/{userID}")
	@PreAuthorize("#oauth2.hasScope('write')")
	public ResponseEntity<CommandResponse> removeUser(@PathVariable String userID){
		try {
			RemoveUserCommand removeUserCommand = new RemoveUserCommand(userID);
			commandGateway.send(removeUserCommand); // non blocking
			log.debug("removeUser: User {} removed Succesfuly",userID);
			return new ResponseEntity<CommandResponse>(new CommandResponse("User removed Succesfuly"),HttpStatus.CREATED );
		}catch (Exception e) {
			log.error("removeUser: Exception occurred "+e);
			return new ResponseEntity<CommandResponse>(new CommandResponse("User can not be removed"),HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
}
