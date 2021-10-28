package com.learning.usersservice.controllers;

import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.usersservice.dtos.UserQueryResponse;
import com.learning.usersservice.queries.FindAllUsersQuery;
import com.learning.usersservice.queries.FindUserByIdQuery;
import com.learning.usersservice.queries.FindUsersBySearchQuery;

@RestController
@RequestMapping("/users")
public class UsersQueryController {

	@Autowired
	private QueryGateway queryGateway;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("#oauth2.hasScope('read')")
	public CompletableFuture<ResponseEntity<UserQueryResponse>> findAll() {
		return queryGateway.query(new FindAllUsersQuery(), UserQueryResponse.class)
						   .thenApply(res -> {
							   if(res.getUsers() == null || res.getUsers().isEmpty())
							   {
								 return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
							   }else {
								  return  new ResponseEntity<>(res, HttpStatus.OK);
							   }
						   })
				;
	}
	
	@GetMapping(value = "/{userID}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public CompletableFuture<ResponseEntity<UserQueryResponse>> findAll(@PathVariable String userID) {
		return queryGateway.query(new FindUserByIdQuery(userID), UserQueryResponse.class)
				 .thenApply(res -> {
					   if(res.getUsers() == null || res.getUsers().isEmpty())
					   {
						 return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
					   }else {
						  return  new ResponseEntity<>(res, HttpStatus.OK);
					   }
				   })
				;
	}
	
	@GetMapping(value = "/search/{text}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public CompletableFuture<ResponseEntity<UserQueryResponse>> findSearch(@PathVariable String text) {
		return queryGateway.query(new FindUsersBySearchQuery(text), UserQueryResponse.class)
				 .thenApply(res -> {
					   if(res.getUsers() == null || res.getUsers().isEmpty())
					   {
						 return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
					   }else {
						  return  new ResponseEntity<>(res, HttpStatus.OK);
					   }
				   })
				;
	}
}
