package com.learning.cqrs.commands;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//this gets called just after commandgateway.send method and before message is pushed to command bus
public class ProductCommandMessageInterceptor implements MessageDispatchInterceptor<CommandMessage<?>>{

	//gets called for all
	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {

		return (index,commandMessage) -> {
			log.info("handle: interceptor called for command type: "+commandMessage.getPayloadType());
			if(commandMessage.getPayloadType().equals(CreateProductCommand.class)) {
				CreateProductCommand createProductCommand = (CreateProductCommand)commandMessage.getPayload();
				if(StringUtils.isEmpty(createProductCommand.getProductId())) {
					throw new IllegalArgumentException("createProductCommand: productId can not be empty while creating product");
				}
				
				if(createProductCommand.getPrice() <= 0) {
					throw new IllegalArgumentException("createProductCommand: price should be greater than zero");
				}
				
				if(createProductCommand.getQuantity() < 1) {
					throw new IllegalArgumentException("createProductCommand: quantity should be greater than zero");
				}
			}
			return commandMessage;
		};
	}

}
