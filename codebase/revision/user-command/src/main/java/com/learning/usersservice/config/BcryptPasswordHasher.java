package com.learning.usersservice.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptPasswordHasher implements PasswordHasher {

	BCryptPasswordEncoder hasher = new BCryptPasswordEncoder(12);
	@Override
	public String encode(String password) {
		return hasher.encode(password);
	}

}
