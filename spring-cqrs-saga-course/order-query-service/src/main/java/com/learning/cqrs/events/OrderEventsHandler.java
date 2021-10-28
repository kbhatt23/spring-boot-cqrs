package com.learning.cqrs.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.learning.cqrs.beans.OrderStatus;
import com.learning.cqrs.entities.OrderEntity;
import com.learning.cqrs.entities.OrdersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ProcessingGroup("order-service-group")
public class OrderEventsHandler {
	@Autowired
	private OrdersRepository ordersRepository;

	@EventHandler
	public void on(CreateOrderEvent createOrderEvent) {
		log.info("on: recieved createOrderEvent: "+createOrderEvent);
		
		String orderId = createOrderEvent.getOrderId();
		if(StringUtils.isEmpty(orderId) || ordersRepository.existsById(orderId)) {
			throw new IllegalArgumentException("createOrderEvent: invalid orderId passed "+orderId);
		}
		OrderEntity orderEntity = new OrderEntity();
		BeanUtils.copyProperties(createOrderEvent, orderEntity);
		ordersRepository.save(orderEntity);
	}
	
	@EventHandler
	public void on(SuccessOrderEvent successOrderEvent) {

		log.info("on: recieved successOrderEvent: "+successOrderEvent);
		
		String orderId = successOrderEvent.getOrderId();
		if(StringUtils.isEmpty(orderId)) {
			throw new IllegalArgumentException("successOrderEvent: invalid orderId passed "+orderId);
		}

		ordersRepository.findById(orderId)
		.map(order -> {
			order.setShipmentPreference(successOrderEvent.getShipmentPreference());
			order.setOrderStatus(OrderStatus.APPROVED);
		return order;
		})
		.map(ordersRepository :: save)
		.orElseThrow(() ->  new IllegalArgumentException("successOrderEvent: invalid productID passed "+orderId))
		;
	}
	
	@EventHandler
	public void on(FailureOrderEvent failureOrderEvent) {

		log.info("on: recieved failureOrderEvent: "+failureOrderEvent);
		
		String orderId = failureOrderEvent.getOrderId();
		if(StringUtils.isEmpty(orderId)) {
			throw new IllegalArgumentException("failureOrderEvent: invalid orderId passed "+orderId);
		}

		ordersRepository.findById(orderId)
		.map(order -> {
			order.setOrderStatus(OrderStatus.REJECTED);
		return order;
		})
		.map(ordersRepository :: save)
		.orElseThrow(() ->  new IllegalArgumentException("failureOrderEvent: invalid productID passed "+orderId))
		;
	}
}
