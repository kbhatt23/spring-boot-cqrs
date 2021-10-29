package com.learning.cqrs;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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

	
	//config for snapshot
	//after every 4 count it creates snapshot
	@Bean
	public SnapshotTriggerDefinition productSnapshotTriggerDefinition(Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 4);
	}
}
