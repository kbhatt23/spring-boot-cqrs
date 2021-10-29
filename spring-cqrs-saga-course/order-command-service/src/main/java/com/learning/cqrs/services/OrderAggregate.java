package com.learning.cqrs.services;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.learning.cqrs.beans.OrderStatus;
import com.learning.cqrs.commands.CreateOrderCommand;
import com.learning.cqrs.commands.FailureOrderCommand;
import com.learning.cqrs.commands.SuccessOrderCommand;
import com.learning.cqrs.events.CreateOrderEvent;
import com.learning.cqrs.events.FailureOrderEvent;
import com.learning.cqrs.events.SuccessOrderEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class OrderAggregate {

	@AggregateIdentifier
	private String orderId;

	private String productId;

	private int quantity;

	private String userId;

	private OrderStatus orderStatus;
	
	private String shipmentPreference;

	public OrderAggregate() {

	}

	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {
		log.info("createOrderCommand called with data: " + createOrderCommand);
		// validations
		if (StringUtils.isEmpty(createOrderCommand.getOrderId())) {
			throw new IllegalArgumentException(
					"createOrderCommand: orderID can not be empty while creating order");
		}
		if (StringUtils.isEmpty(createOrderCommand.getProductId())) {
			throw new IllegalArgumentException(
					"createOrderCommand: productId can not be empty while creating order");
		}
		if (StringUtils.isEmpty(createOrderCommand.getUserId())) {
			throw new IllegalArgumentException(
					"createOrderCommand: user can not be empty while creating order");
		}
		if(createOrderCommand.getQuantity() < 1) {
			throw new IllegalArgumentException("createOrderCommand: quantity should be greater than zero");
		}
		
		CreateOrderEvent CreateOrderEvent = new CreateOrderEvent();
		BeanUtils.copyProperties(createOrderCommand, CreateOrderEvent);
		AggregateLifecycle.apply(CreateOrderEvent);
	}
	
	@EventSourcingHandler
	public void on(CreateOrderEvent createOrderEvent) {
		log.info("createOrderEvent called with data: " + createOrderEvent);
		
		this.orderId=createOrderEvent.getOrderId();
		this.productId=createOrderEvent.getProductId();
		this.quantity=createOrderEvent.getQuantity();
		this.userId=createOrderEvent.getUserId();
		this.orderStatus = createOrderEvent.getOrderStatus();
		this.shipmentPreference=createOrderEvent.getShipmentPreference();
	}
	
	@CommandHandler
	public void handle(SuccessOrderCommand successOrderCommand) {
		log.info("successOrderCommand called with data: " + successOrderCommand);
		// validations
		if (StringUtils.isEmpty(successOrderCommand.getOrderId())) {
			throw new IllegalArgumentException(
					"successOrderCommand: orderID can not be empty while finalizing order");
		}
		//can be used to validate saga rollback
		if (StringUtils.isEmpty(successOrderCommand.getShipmentPreference())) {
			throw new IllegalArgumentException(
					"successOrderCommand: shipmentPreference can not be empty while finalizing order");
		}
		
		SuccessOrderEvent successOrderEvent = new SuccessOrderEvent();
		BeanUtils.copyProperties(successOrderCommand, successOrderEvent);
		AggregateLifecycle.apply(successOrderEvent);
		
			
	}
	
	@EventSourcingHandler
	public void on(SuccessOrderEvent successOrderEvent) {
		log.info("successOrderEvent called with data: " + successOrderEvent);
		
		this.orderStatus = OrderStatus.APPROVED;
		this.shipmentPreference=successOrderEvent.getShipmentPreference();
	}
	
	@CommandHandler
	public void handle(FailureOrderCommand failureOrderCommand) {
		log.info("failureOrderCommand called with data: " + failureOrderCommand);
		// validations
		if (StringUtils.isEmpty(failureOrderCommand.getOrderId())) {
			throw new IllegalArgumentException(
					"successOrderCommand: orderID can not be empty while finalizing order");
		}
		
		FailureOrderEvent failureOrderEvent = new FailureOrderEvent();
		BeanUtils.copyProperties(failureOrderCommand, failureOrderEvent);
		AggregateLifecycle.apply(failureOrderEvent);
		
			
	}
	
	@EventSourcingHandler
	public void on(FailureOrderEvent failureOrderEvent) {
		log.info("failureOrderEvent called with data: " + failureOrderEvent);
		this.orderStatus = OrderStatus.REJECTED;
	}
	
}
