package com.learning.usersservice.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.usersservice.dtos.UserQueryResponse;
import com.learning.usersservice.models.User;
import com.learning.usersservice.queries.FindAllUsersQuery;
import com.learning.usersservice.queries.FindUserByIdQuery;
import com.learning.usersservice.queries.FindUsersBySearchQuery;
import com.learning.usersservice.repositories.UsersRepository;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

	@Autowired
	private UsersRepository usersRepository;
	
	@QueryHandler
	@Override
	public UserQueryResponse find(FindAllUsersQuery query) {
		return new UserQueryResponse(usersRepository.findAll());	
	}

	@QueryHandler
	@Override
	public UserQueryResponse find(FindUserByIdQuery query) {
		List<User> users = usersRepository.findById(query.getId())
							.map(user -> Arrays.asList(user))
							.orElse(new ArrayList<>());
		return new UserQueryResponse(users);
	}

	@QueryHandler
	@Override
	public UserQueryResponse find(FindUsersBySearchQuery query) {
		return new UserQueryResponse(usersRepository.findByFilterRegex(query.getText()));	
	}

}
