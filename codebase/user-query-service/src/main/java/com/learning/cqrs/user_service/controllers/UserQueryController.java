package com.learning.cqrs.user_service.controllers;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.user_service.dtos.UserQueryResponse;
import com.learning.cqrs.user_service.queries.FindAllUserQuery;
import com.learning.cqrs.user_service.queries.FindUserByIdQuery;
import com.learning.cqrs.user_service.queries.SearchUsersQuery;

@RestController
@RequestMapping("/api/v1/users")
public class UserQueryController {

	@Autowired
	private QueryGateway queryGateway;
	
	@GetMapping
	@PreAuthorize("#oauth2.hasScope('read')")
	public UserQueryResponse findAll() {
		 return  queryGateway.query(new FindAllUserQuery(), ResponseTypes.instanceOf(UserQueryResponse.class)).join();
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public UserQueryResponse findById(@PathVariable String userId) {
		 UserQueryResponse res = queryGateway.query(new FindUserByIdQuery(userId), ResponseTypes.instanceOf(UserQueryResponse.class)).join();
		 return res;
	}
	
	@GetMapping("/filter/{filter}")
	@PreAuthorize("#oauth2.hasScope('read')")
	public UserQueryResponse findByQueryFilter(@PathVariable String filter) {
		return  queryGateway.query(new SearchUsersQuery(filter), ResponseTypes.instanceOf(UserQueryResponse.class)).join();
	}
}
