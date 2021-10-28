package com.learning.cqrs.queryhandlers;

import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cqrs.entities.AllProductsResponse;
import com.learning.cqrs.entities.ProductEntity;
import com.learning.cqrs.entities.ProductsRepository;
import com.learning.cqrs.queries.FindAllProductsQuery;
import com.learning.cqrs.queries.FindProductByIDQuery;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductQueryHandler {
	
	@Autowired
	private ProductsRepository productsRepository;

	@QueryHandler
	public CompletableFuture<AllProductsResponse> on(FindAllProductsQuery findAllProductsQuery){
		log.info("on: findAllProductsQuery called");
		
		return CompletableFuture.supplyAsync(productsRepository :: findAll)
				.thenApply(products -> new AllProductsResponse(products))
				;
	}
	
	@QueryHandler
	public CompletableFuture<ProductEntity> on(FindProductByIDQuery findProductByIDQuery){
		String productID = findProductByIDQuery.getProductID();
		log.info("on: findProductByIDQuery called for id "+productID);
		
		return CompletableFuture.supplyAsync(() -> productsRepository.findById(productID).orElseThrow(() -> new IllegalArgumentException("productid "+productID+" do not exist in D.B")));
	}
}
