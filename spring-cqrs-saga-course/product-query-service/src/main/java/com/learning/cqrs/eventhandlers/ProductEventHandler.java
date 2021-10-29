package com.learning.cqrs.eventhandlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.learning.cqrs.entities.ProductEntity;
import com.learning.cqrs.entities.ProductsRepository;
import com.learning.cqrs.events.CreateProductEvent;
import com.learning.cqrs.events.DeleteProductEvent;
import com.learning.cqrs.events.ReserveProductEvent;
import com.learning.cqrs.events.UndoReserveProductEvent;
import com.learning.cqrs.events.UpdateProductEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ProcessingGroup("product-service-group")
public class ProductEventHandler {

	@Autowired
	private ProductsRepository productsRepository;
	
	@EventHandler
	public void on(CreateProductEvent createProductEvent) {
		log.info("on: recieved createProductEvent: "+createProductEvent);
		String productId = createProductEvent.getProductId();
		if(StringUtils.isEmpty(productId) || productsRepository.existsById(productId)) {
			throw new IllegalArgumentException("createProductEvent: invalid productID passed "+productId);
		}
		ProductEntity entity = new ProductEntity();
		BeanUtils.copyProperties(createProductEvent, entity);
		productsRepository.save(entity);
	}
	
	@EventHandler
	public void on(UpdateProductEvent updateProductEvent) {
		log.info("on: recieved updateProductEvent: "+updateProductEvent);
		String productId = updateProductEvent.getProductId();
		if(StringUtils.isEmpty(productId) || !productsRepository.existsById(productId)) {
			throw new IllegalArgumentException("updateProductEvent: invalid productID passed "+productId);
		}
		ProductEntity entity = new ProductEntity();
		BeanUtils.copyProperties(updateProductEvent, entity);
		productsRepository.save(entity);
	}
	
	@EventHandler
	public void on(DeleteProductEvent deleteProductEvent) {
		log.info("on: recieved deleteProductEvent: "+deleteProductEvent);
		String productId = deleteProductEvent.getProductId();
		if(StringUtils.isEmpty(productId) || !productsRepository.existsById(productId)) {
			throw new IllegalArgumentException("deleteProductEvent: invalid productID passed "+productId);
		}
		
		productsRepository.deleteById(productId);
	}
	@EventHandler
	public void on(ReserveProductEvent reserveProductEvent) {
		log.info("on: recieved reserveProductEvent: "+reserveProductEvent);
		String productId = reserveProductEvent.getProductId();
		if(StringUtils.isEmpty(productId) ) {
			throw new IllegalArgumentException("reserveProductEvent: invalid productID passed "+productId);
		}
		
		
		productsRepository.findById(productId)
							.map(product ->{ product.setQuantity(product.getQuantity() - reserveProductEvent.getQuantity()); return product;})
							.map(productsRepository :: save)
							.orElseThrow(() ->  new IllegalArgumentException("reserveProductEvent: invalid productID passed "+productId))
							;
	}
	
	@EventHandler
	public void on(UndoReserveProductEvent undoReserveProductEvent) {
		log.info("on: recieved undoReserveProductEvent: "+undoReserveProductEvent);
		String productId = undoReserveProductEvent.getProductId();
		if(StringUtils.isEmpty(productId) ) {
			throw new IllegalArgumentException("undoReserveProductEvent: invalid productID passed "+productId);
		}
		
		
		productsRepository.findById(productId)
							.map(product ->{ product.setQuantity(product.getQuantity() + undoReserveProductEvent.getQuantity()); return product;})
							.map(productsRepository :: save)
							.orElseThrow(() ->  new IllegalArgumentException("undoReserveProductEvent: invalid productID passed "+productId))
							;
	}

	//will be called when replay event is triggered only once at the begining as step1
	@ResetHandler
	public void reset() {
		log.info("reset called");
		productsRepository.deleteAll();
	}
}

