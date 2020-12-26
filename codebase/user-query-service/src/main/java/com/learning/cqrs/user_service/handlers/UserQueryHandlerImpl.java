package com.learning.cqrs.user_service.handlers;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cqrs.user_service.dtos.UserQueryResponse;
import com.learning.cqrs.user_service.models.User;
import com.learning.cqrs.user_service.queries.FindAllUserQuery;
import com.learning.cqrs.user_service.queries.FindUserByIdQuery;
import com.learning.cqrs.user_service.queries.SearchUsersQuery;
import com.learning.cqrs.user_service.repositories.UserRepository;

@Service
public class UserQueryHandlerImpl implements UserQueryHandler {

	@Autowired
	private UserRepository repository;
	
	@Override
	@QueryHandler
	public UserQueryResponse handle(FindAllUserQuery query) {
		return new UserQueryResponse(repository.findAll());
	}

	@QueryHandler
	@Override
	public UserQueryResponse handle(FindUserByIdQuery query) {
		User userFound = repository.findById(query.getId())
				.orElse(null);
		;
		return new UserQueryResponse(userFound);
	}

	@QueryHandler
	@Override
	public UserQueryResponse handle(SearchUsersQuery query) {
		List<User> users = repository.findByFilterRegex(query.getFilter());
		return new UserQueryResponse(users);
	}

}
