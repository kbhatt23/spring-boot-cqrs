package com.learning.cqrs.controllers;

import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.beans.UserDTO;
import com.learning.cqrs.queries.FindUserByIDQuery;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("/{userId}")
	public CompletableFuture<UserDTO> findOrderById(@PathVariable String userId){
		return queryGateway.query(new FindUserByIDQuery	(userId), UserDTO.class);
	}
}
