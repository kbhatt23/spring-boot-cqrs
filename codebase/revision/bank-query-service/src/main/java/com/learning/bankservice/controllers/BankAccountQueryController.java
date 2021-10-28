package com.learning.bankservice.controllers;

import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.bankservice.dtos.BankQueryResponse;
import com.learning.bankservice.queries.FindAccountByIdQuery;
import com.learning.bankservice.queries.FindAccountBySearchQuery;
import com.learning.bankservice.queries.FindAllAccountsQuery;

@RestController
@RequestMapping("/bank")
public class BankAccountQueryController {

	@Autowired
	private QueryGateway queryGateway;
	
	@GetMapping
	public CompletableFuture<ResponseEntity<BankQueryResponse>> findAll(){
		
		return queryGateway.query(new FindAllAccountsQuery(), BankQueryResponse.class)
				  .thenApply(res -> {
					   if(res.getAccounts() == null || res.getAccounts().isEmpty())
					   {
						 return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
					   }else {
						  return  new ResponseEntity<>(res, HttpStatus.OK);
					   }
				   })
				;
	}
	
	@GetMapping("/{accountId}")
	public CompletableFuture<ResponseEntity<BankQueryResponse>> findAByID(@PathVariable String accountId){
		
		return queryGateway.query(new FindAccountByIdQuery(accountId), BankQueryResponse.class)
				  .thenApply(res -> {
					   if(res.getAccounts() == null || res.getAccounts().isEmpty())
					   {
						 return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
					   }else {
						  return  new ResponseEntity<>(res, HttpStatus.OK);
					   }
				   })
				;
	}
	
	@GetMapping(value = "/search/{text}")
	public CompletableFuture<ResponseEntity<BankQueryResponse>> findSearch(@PathVariable String text) {
		return queryGateway.query(new FindAccountBySearchQuery(text), BankQueryResponse.class)
				 .thenApply(res -> {
					   if(res.getAccounts() == null || res.getAccounts().isEmpty())
					   {
						 return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
					   }else {
						  return  new ResponseEntity<>(res, HttpStatus.OK);
					   }
				   })
				;
	}
	
}
