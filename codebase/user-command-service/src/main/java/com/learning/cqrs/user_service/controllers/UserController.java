package com.learning.cqrs.user_service.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.user_service.commands.UserCreationCommand;
import com.learning.cqrs.user_service.commands.UserDeletionCommand;
import com.learning.cqrs.user_service.commands.UserUpdationCommand;
import com.learning.cqrs.user_service.dtos.BaseResponse;
import com.learning.cqrs.user_service.dtos.UserCreationResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private CommandGateway commandGateway;
	
	@PostMapping
	public ResponseEntity<UserCreationResponse> createUser(@RequestBody @Valid UserCreationCommand userCreationCommand){
		String uniqueID = UUID.randomUUID().toString();
		try {
			userCreationCommand.setId(uniqueID);
			commandGateway.send(userCreationCommand);
			//below is blocking in nature
			//commandGateway.sendAndWait(userCreationCommand);
			return new ResponseEntity<UserCreationResponse>(new UserCreationResponse("User registered succesfully with ID: "+userCreationCommand.getId(),uniqueID), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<UserCreationResponse>(new UserCreationResponse("Error while creating user with ID: "+userCreationCommand.getId(),uniqueID), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<BaseResponse> updateUser(@RequestBody @Valid UserUpdationCommand userUpdationCommand, @PathVariable String userId){
		try {
		//update the command id, in commandhandler we will take this and copy to the user id
		userUpdationCommand.setId(userId);
		commandGateway.send(userUpdationCommand);
		return new ResponseEntity<BaseResponse>(new BaseResponse("User updated succesfully with ID: "+userUpdationCommand.getId()), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse("Error while updating user with ID: "+userUpdationCommand.getId()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<BaseResponse> deleteUser(@PathVariable String userId){
		UserDeletionCommand userDeletionCommand = new UserDeletionCommand();
		try {
			userDeletionCommand.setId(userId);
			commandGateway.send(userDeletionCommand);
			return new ResponseEntity<BaseResponse>(new BaseResponse("User removed succesfully with ID: "+userDeletionCommand.getId()), HttpStatus.NO_CONTENT);
			}catch (Exception e) {
				return new ResponseEntity<BaseResponse>(new BaseResponse("Error while removing user with ID: "+userDeletionCommand.getId()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
}
