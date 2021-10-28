package com.learning.usersservice.handlers;

import com.learning.usersservice.dtos.UserQueryResponse;
import com.learning.usersservice.queries.FindAllUsersQuery;
import com.learning.usersservice.queries.FindUserByIdQuery;
import com.learning.usersservice.queries.FindUsersBySearchQuery;

public interface UserQueryHandler {

	UserQueryResponse find(FindAllUsersQuery query);
	
	UserQueryResponse find(FindUserByIdQuery query);
	
	UserQueryResponse find(FindUsersBySearchQuery query);
}
