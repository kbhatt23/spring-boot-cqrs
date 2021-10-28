package com.learning.usersservice.dtos;

import java.util.List;

import com.learning.usersservice.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryResponse {

	private List<User> users;
}
