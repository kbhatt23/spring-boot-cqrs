package com.learning.cqrs.user_service.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
//@Order(2)
public class BasicAuthenticationConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("Using BasicAuthenticationConfig for security");
		
		http
			.authorizeRequests()
			.antMatchers("/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.csrf().disable()
			;
	}
	}
