package com.learning.cqrs.queries;

import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cqrs.beans.OrderDTO;
import com.learning.cqrs.entities.OrdersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderQueryHandler {

	@Autowired
	private OrdersRepository ordersRepository;
	
	@QueryHandler
	public CompletableFuture<OrderDTO> query(FindOrderById findOrderById){
		String orderID = findOrderById.getOrderId();
		log.info("query: orderID called for id "+orderID);
		
		return CompletableFuture.supplyAsync(() -> ordersRepository.findById(orderID).orElseThrow(() -> new IllegalArgumentException("orderId "+orderID+" do not exist in D.B")))
				.thenApply(orderEntity -> {OrderDTO order = new OrderDTO(); BeanUtils.copyProperties(orderEntity, order); return order;});
				
	}
}
