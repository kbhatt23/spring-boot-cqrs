package com.learning.cqrs.user_service.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {

	@Override
	public String hashPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		return encoder.encode(password);
	}

}
