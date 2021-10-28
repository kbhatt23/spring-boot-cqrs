package com.learning.cqrs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {
	
	@Id
	@Column(name = "product_id")
	private String productId;

	@Column(name = "product_name")
	private String name;
	
	@Column(name = "product_price")
	private double price;
	
	@Column(name = "product_descripion")
	private String descripion;
	
	@Column(name = "product_quantity")
	private int quantity;

}
