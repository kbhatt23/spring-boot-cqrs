package com.learning.cqrs.controllers;

import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.beans.OrderDTO;
import com.learning.cqrs.queries.FindOrderById;


@RequestMapping("/orders")
@RestController
public class OrdersController {
	
	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("/{orderId}")
	public CompletableFuture<OrderDTO> findOrderById(@PathVariable String orderId){
		return queryGateway.query(new FindOrderById(orderId), OrderDTO.class);
	}
}
