package com.learning.cqrs.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.beans.UserDTO;
import com.learning.cqrs.commands.CreateUserCommand;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	private CommandGateway commandGateway;

	@PostMapping
	public CompletableFuture<ResponseEntity<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
		log.info("createUser: creating user with data "+userDTO);
		
		String userId = UUID.randomUUID().toString();
		CreateUserCommand createUserCommand = CreateUserCommand.builder().userId(userId)
					.name(userDTO.getName())
					.balance(userDTO.getBalance())
					.build();
		userDTO.setUserId(userId);
		try {
			return commandGateway.send(createUserCommand)
					.thenApply(
					obj -> new ResponseEntity<>( userDTO, HttpStatus.CREATED))
					.exceptionally(error -> {
						log.error("createUser: exception occurred during gateway: "+error);
						return new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR);} )
					;
		} catch (Exception e) {
			log.error("createUser: exception occurred: "+e);
			return CompletableFuture
					.supplyAsync(() -> new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

}
