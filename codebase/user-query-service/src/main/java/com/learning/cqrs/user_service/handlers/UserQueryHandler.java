package com.learning.cqrs.user_service.handlers;

import com.learning.cqrs.user_service.dtos.UserQueryResponse;
import com.learning.cqrs.user_service.queries.FindAllUserQuery;
import com.learning.cqrs.user_service.queries.FindUserByIdQuery;
import com.learning.cqrs.user_service.queries.SearchUsersQuery;

public interface UserQueryHandler {

	public UserQueryResponse handle(FindAllUserQuery query);
	
	public UserQueryResponse handle(FindUserByIdQuery query);
	
	public UserQueryResponse handle(SearchUsersQuery query);
}
