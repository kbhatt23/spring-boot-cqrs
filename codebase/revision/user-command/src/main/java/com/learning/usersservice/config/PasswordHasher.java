package com.learning.usersservice.config;

public interface PasswordHasher {

	String encode(String password);
}
