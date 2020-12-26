package com.learning.cqrs.user_service.security;

public interface PasswordEncoder {

	String hashPassword(String password);
}
