package com.learning.cqrs.user_service.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchUsersQuery {

	private String filter;
}
