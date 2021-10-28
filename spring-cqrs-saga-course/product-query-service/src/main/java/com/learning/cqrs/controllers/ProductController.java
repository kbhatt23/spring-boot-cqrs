package com.learning.cqrs.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.entities.AllProductsResponse;
import com.learning.cqrs.entities.ProductEntity;
import com.learning.cqrs.queries.FindAllProductsQuery;
import com.learning.cqrs.queries.FindProductByIDQuery;


@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private QueryGateway queryGateway;
	
	
	@GetMapping("/{productID}")
	public CompletableFuture<ProductEntity> findById(@PathVariable String productID){
		return queryGateway.query(new FindProductByIDQuery(productID), ProductEntity.class)
				;
	}
	
	
	@GetMapping
	public CompletableFuture<AllProductsResponse> findAll(){
		return queryGateway.query(new FindAllProductsQuery(), AllProductsResponse.class);
	}
}
