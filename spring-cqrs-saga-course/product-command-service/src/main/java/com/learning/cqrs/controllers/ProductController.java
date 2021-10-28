package com.learning.cqrs.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.beans.Product;
import com.learning.cqrs.commands.CreateProductCommand;
import com.learning.cqrs.commands.DeleteProductCommand;
import com.learning.cqrs.commands.UpdateProductCommand;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

	@Autowired
	private CommandGateway commandGateway;

	@PostMapping
	public CompletableFuture<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
		log.info("createProduct: creating product with data "+product);
		
		String productId = UUID.randomUUID().toString();
		CreateProductCommand createProductCommand = CreateProductCommand.builder().name(product.getName())
				.descripion(product.getDescripion()).price(product.getPrice()).productId(productId)
				.quantity(product.getQuantity())
				.build();
		product.setProductId(productId);
		try {
			return commandGateway.send(createProductCommand)
					.thenApply(
					obj -> new ResponseEntity<>( product, HttpStatus.CREATED))
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
	
	@PutMapping
	public CompletableFuture<ResponseEntity<Product>> updateProduct(@RequestBody Product product) {
		log.info("updateProduct: updating product with data "+product);
		
		UpdateProductCommand updateProductCommand = UpdateProductCommand.builder().name(product.getName())
				.descripion(product.getDescripion()).price(product.getPrice()).productId(product.getProductId())
				.quantity(product.getQuantity())
				.build();
		try {
			return commandGateway.send(updateProductCommand)
					.thenApply(
					obj -> new ResponseEntity<>( product, HttpStatus.OK))
					.exceptionally(error -> {
						log.error("updateProduct: exception occurred during gateway: "+error);
						return new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR);} )
					;
		} catch (Exception e) {
			log.error("updateProduct: exception occurred: "+e);
			return CompletableFuture
					.supplyAsync(() -> new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@DeleteMapping("/{productID}")
	public CompletableFuture<ResponseEntity<Object>> deleteProduct(@PathVariable String productID) {
		log.info("deleteProduct: deleting product with id "+productID);
		
		DeleteProductCommand deleteProductCommand = new DeleteProductCommand(productID);
		try {
			return commandGateway.send(deleteProductCommand)
					.thenApply(
					obj -> new ResponseEntity<>( null, HttpStatus.NO_CONTENT))
					.exceptionally(error -> {
						log.error("deleteProduct: exception occurred during gateway: "+error);
						return new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR);} )
					;
		} catch (Exception e) {
			log.error("deleteProduct: exception occurred: "+e);
			return CompletableFuture
					.supplyAsync(() -> new ResponseEntity<>(null,
							HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
}
