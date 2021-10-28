package com.learning.cqrs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.learning.cqrs.beans.OrderStatus;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {

	@Id
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "product_id")
	private String productId;
	
	private int quantity;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "order_status")
	private OrderStatus orderStatus;
	
	@Column(name = "order_shipment_preference")
	private String shipmentPreference;
}
