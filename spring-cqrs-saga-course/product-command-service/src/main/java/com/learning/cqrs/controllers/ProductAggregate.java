package com.learning.cqrs.controllers;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.learning.cqrs.commands.CreateProductCommand;
import com.learning.cqrs.commands.DeleteProductCommand;
import com.learning.cqrs.commands.ReserveProductCommand;
import com.learning.cqrs.commands.UndoReserveProductCommand;
import com.learning.cqrs.commands.UpdateProductCommand;
import com.learning.cqrs.events.CreateProductEvent;
import com.learning.cqrs.events.DeleteProductEvent;
import com.learning.cqrs.events.ReserveProductEvent;
import com.learning.cqrs.events.UndoReserveProductEvent;
import com.learning.cqrs.events.UpdateProductEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class ProductAggregate {

	@AggregateIdentifier
	private String productId;

	private String name;

	private double price;

	private String descripion;
	
	private int quantity;
	
	public ProductAggregate() {
		
	}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		log.info("createProductCommand called with data: "+createProductCommand);
		
		//can do validations here and throw exception if needed
		//for better clarity and single responsibility/chain of responsibility moved to message interceptor
//		if(StringUtils.isEmpty(createProductCommand.getProductId())) {
//			throw new IllegalArgumentException("createProductCommand: productId can not be empty while creating product");
//		}
//		
//		if(createProductCommand.getPrice() <= 0) {
//			throw new IllegalArgumentException("createProductCommand: price should be greater than zero");
//		}
		
		CreateProductEvent createProductEvent = new CreateProductEvent();
		BeanUtils.copyProperties(createProductCommand, createProductEvent);
		
		AggregateLifecycle.apply(createProductEvent);
	}
	
	//tis will get called only if event sourcing(saving event to h2/mongo/mysql etc) and event bus push was successful
	@EventSourcingHandler
	public void on(CreateProductEvent createProductEvent) {
		log.info("createProductEvent called with data: "+createProductEvent);
		this.productId = createProductEvent.getProductId();
		this.name = createProductEvent.getName();
		this.price = createProductEvent.getPrice();
		this.descripion = createProductEvent.getDescripion();
		this.quantity = createProductEvent.getQuantity();
	}
	
	//for update if aggregateid do not match it wont even come here
	@CommandHandler
	public void handle(UpdateProductCommand updateProductCommand) {
		log.info("updateProductCommand called with data: "+updateProductCommand);
		
		//basic validations
		if(StringUtils.isEmpty(updateProductCommand.getProductId())) {
			throw new IllegalArgumentException("updateProductCommand: productId can not be empty while updating product");
		}
		
		if(updateProductCommand.getPrice() <= 0) {
			throw new IllegalArgumentException("updateProductCommand: price should be greater than zero");
		}
		
		if(updateProductCommand.getQuantity() < 1) {
			throw new IllegalArgumentException("updateProductCommand: quantity should be greater than zero");
		}
		
		UpdateProductEvent updateProductEvent = new UpdateProductEvent();
		BeanUtils.copyProperties(updateProductCommand, updateProductEvent);
		
		AggregateLifecycle.apply(updateProductEvent);
	}
	
	@EventSourcingHandler
	public void on(UpdateProductEvent updateProductEvent) {
		log.info("updateProductEvent called with data: "+updateProductEvent);
		
		//update everything else id as that is aggregate id
		this.name = updateProductEvent.getName();
		this.price = updateProductEvent.getPrice();
		this.descripion = updateProductEvent.getDescripion();
		this.quantity=updateProductEvent.getQuantity();
	}
	
	@CommandHandler
	public void handle(DeleteProductCommand deleteProductCommand) {
		log.info("deleteProductCommand called with data: "+deleteProductCommand);
		
		//basic validations
		if(StringUtils.isEmpty(deleteProductCommand.getProductId())) {
			throw new IllegalArgumentException("deleteProductCommand: productId can not be empty while deleting product");
		}
		
		DeleteProductEvent deleteProductEvent = new DeleteProductEvent(deleteProductCommand.getProductId());
		AggregateLifecycle.apply(deleteProductEvent);
	}
	
	@EventSourcingHandler
	public void on(DeleteProductEvent deleteProductEvent) {
		log.info("deleteProductEvent called with data: "+deleteProductEvent);
		
		AggregateLifecycle.markDeleted();
		
	}
	
	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {
		log.info("reserveProductCommand called with data: "+reserveProductCommand);
		
		//handle exception
		if(StringUtils.isEmpty(reserveProductCommand.getProductId())) {
			throw new IllegalArgumentException("reserveProductCommand: productId can not be empty while reserving product");
		}
		
		if(StringUtils.isEmpty(reserveProductCommand.getUserId())) {
			throw new IllegalArgumentException("reserveProductCommand: userId can not be empty while reserving product");
		}
		

		if(StringUtils.isEmpty(reserveProductCommand.getOrderId())) {
			throw new IllegalArgumentException("reserveProductCommand: orderId can not be empty while reserving product");
		}
		
		int quantityToReserve = reserveProductCommand.getQuantity();
		if(quantityToReserve < 1) {
			throw new IllegalArgumentException("reserveProductCommand: quantity should be greater than zero");
		}
		
		//this is there to test saga rollback scenario
		if(this.quantity < quantityToReserve) {
			throw new IllegalArgumentException("reserveProductCommand: can not reserve more than in stock");
		}
		
		ReserveProductEvent reserveProductEvent = new ReserveProductEvent();
		BeanUtils.copyProperties(reserveProductCommand, reserveProductEvent);
		
		AggregateLifecycle.apply(reserveProductEvent);
	}
	
	@EventSourcingHandler
	public void on(ReserveProductEvent reserveProductEvent) {
		log.info("reserveProductEvent called with data: "+reserveProductEvent);
		
		//update the local state and axon will push to event bus for query api to update
		this.quantity -= reserveProductEvent.getQuantity();
	}
	
	@CommandHandler
	public void handle(UndoReserveProductCommand undoReserveProductCommand) {
		log.info("undoReserveProductCommand called with data: "+undoReserveProductCommand);
		
		//handle exception
		if(StringUtils.isEmpty(undoReserveProductCommand.getProductId())) {
			throw new IllegalArgumentException("undoReserveProductCommand: productId can not be empty while reserving product");
		}
		
		if(StringUtils.isEmpty(undoReserveProductCommand.getUserId())) {
			throw new IllegalArgumentException("undoReserveProductCommand: userId can not be empty while reserving product");
		}
		

		if(StringUtils.isEmpty(undoReserveProductCommand.getOrderId())) {
			throw new IllegalArgumentException("undoReserveProductCommand: orderId can not be empty while reserving product");
		}
		
		UndoReserveProductEvent undoReserveProductEvent = new UndoReserveProductEvent();
		BeanUtils.copyProperties(undoReserveProductCommand, undoReserveProductEvent);
		
		AggregateLifecycle.apply(undoReserveProductEvent);
	}
	
	@EventSourcingHandler
	public void on(UndoReserveProductEvent undoReserveProductEvent) {
		log.info("undoReserveProductEvent called with data: "+undoReserveProductEvent);
		
		//update the local state and axon will push to event bus for query api to update
		this.quantity += undoReserveProductEvent.getQuantity();
	}

	
}
