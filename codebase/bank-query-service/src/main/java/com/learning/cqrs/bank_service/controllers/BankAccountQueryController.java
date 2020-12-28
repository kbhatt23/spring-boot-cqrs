package com.learning.cqrs.bank_service.controllers;

import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.bank_service.dtos.BankAccountQueryResponse;
import com.learning.cqrs.bank_service.queries.FindAccdountByIdQuery;
import com.learning.cqrs.bank_service.queries.FindAccountByfilterQuery;
import com.learning.cqrs.bank_service.queries.FindAllAccountsQuery;

@RestController
@RequestMapping("/api/v1/bank")
public class BankAccountQueryController {

	@Autowired
	private QueryGateway queryGateway;
	
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping
	public CompletableFuture<ResponseEntity<BankAccountQueryResponse>> findAll() {
		//no need to join just return the compleanelfuture
		 return  queryGateway.query(new FindAllAccountsQuery(), ResponseTypes.instanceOf(BankAccountQueryResponse.class))
				 .thenApply(res ->{
					 if(res.getAccounts() == null || res.getAccounts().size()==0) {
						 return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
					 }else {
						 return new ResponseEntity<>(res, HttpStatus.OK);
					 }
				 })
				 
				 ;
	}
	
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping("/{accountId}")
	public CompletableFuture<ResponseEntity<BankAccountQueryResponse>> findAById(@PathVariable String accountId) {
		 return  queryGateway.query(new FindAccdountByIdQuery(accountId), ResponseTypes.instanceOf(BankAccountQueryResponse.class))
				 .thenApply(res ->{
					 if(res.getAccounts() == null || res.getAccounts().size()==0) {
						 return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
					 }else {
						 return new ResponseEntity<>(res, HttpStatus.OK);
					 }
				 })
				 ;
	}
	
	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping("/filter/{filter}")
	public CompletableFuture<ResponseEntity<BankAccountQueryResponse>> findAByFilter(@PathVariable String filter) {
		 return  queryGateway.query(new FindAccountByfilterQuery(filter), ResponseTypes.instanceOf(BankAccountQueryResponse.class))
				 .thenApply(res ->{
					 if(res.getAccounts() == null || res.getAccounts().size()==0) {
						 return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
					 }else {
						 return new ResponseEntity<>(res, HttpStatus.OK);
					 }
				 })
				 ;
	}
	
}
