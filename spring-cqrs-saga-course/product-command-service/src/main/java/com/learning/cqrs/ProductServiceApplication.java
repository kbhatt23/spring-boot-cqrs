package com.learning.cqrs;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.learning.cqrs.commands.ProductCommandMessageInterceptor;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	
	@Autowired
	public void updateMessageInterceptor(ApplicationContext context , CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(ProductCommandMessageInterceptor.class));
	}

}
