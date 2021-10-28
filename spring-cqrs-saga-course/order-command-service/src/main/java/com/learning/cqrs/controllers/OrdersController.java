package com.learning.cqrs.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.beans.OrderDTO;
import com.learning.cqrs.beans.OrderStatus;
import com.learning.cqrs.commands.CreateOrderCommand;

import lombok.extern.slf4j.Slf4j;


@RequestMapping("/orders")
@RestController
@Slf4j
public class OrdersController {
	
	@Autowired
	private CommandGateway commandGateway;

	@PostMapping
	public CompletableFuture<ResponseEntity<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
		log.info("createOrder: creating order with data "+orderDTO);
		
		String orderId = UUID.randomUUID().toString();
		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder().orderId(orderId).quantity(orderDTO.getQuantity())
					.orderStatus(OrderStatus.CREATED)
					.productId(orderDTO.getProductId())
					.userId(orderDTO.getUserId())
					.shipmentPreference(orderDTO.getShipmentPreference())
					.build();
		orderDTO.setOrderId(orderId);
		orderDTO.setOrderStatus(OrderStatus.CREATED);
		try {
			return commandGateway.send(createOrderCommand)
					.thenApply(
					obj -> new ResponseEntity<>( orderDTO, HttpStatus.CREATED))
					.exceptionally(error -> {
						log.error("createProduct: exception occurred during gateway: "+error);
						return new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR);} )
					;
		} catch (Exception e) {
			log.error("createProduct: exception occurred: "+e);
			return CompletableFuture
					.supplyAsync(() -> new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
}
