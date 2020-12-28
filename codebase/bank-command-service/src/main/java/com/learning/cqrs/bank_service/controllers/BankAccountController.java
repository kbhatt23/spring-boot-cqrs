package com.learning.cqrs.bank_service.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cqrs.bank_service.commands.CreateAccountCommand;
import com.learning.cqrs.bank_service.commands.CreditAccountCommand;
import com.learning.cqrs.bank_service.commands.DebitAccountCommand;
import com.learning.cqrs.bank_service.commands.DeleteAccountCommand;
import com.learning.cqrs.bank_service.commands.UpdateAccountProfileCommand;
import com.learning.cqrs.bank_service.dtos.BaseResponse;
import com.learning.cqrs.bank_service.dtos.CreateankAccountResponse;
import com.learning.cqrs.bank_service.models.Profile;

@RestController
@RequestMapping("/api/v1/bank")
public class BankAccountController {

	@Autowired
	private CommandGateway commandGateway;
	
	@PostMapping
	@PreAuthorize("#oauth2.hasScope('write')")
	public CompletableFuture<ResponseEntity<CreateankAccountResponse>> createAccount(@RequestBody @Valid CreateAccountCommand command){
		
		String randomID = UUID.randomUUID().toString();
		try {
		command.setId(randomID);
		//future
		return commandGateway.send(command)
		.thenApply(item -> new ResponseEntity<CreateankAccountResponse>(new CreateankAccountResponse("Account Created succesfully with ID "+randomID, randomID), 
				HttpStatus.CREATED))
			.exceptionally(error -> new ResponseEntity<CreateankAccountResponse>(new CreateankAccountResponse("Error while creating Account with ID "+randomID, randomID), 
					HttpStatus.INTERNAL_SERVER_ERROR))
		;
		
				}
		catch (Exception e) {
			return 
					CompletableFuture.supplyAsync(() ->
					new ResponseEntity<CreateankAccountResponse>(new CreateankAccountResponse("Error while creating Account with ID "+randomID, randomID), 
					HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@PutMapping("/{accountId}")
	@PreAuthorize("#oauth2.hasScope('write')")
	public CompletableFuture<ResponseEntity<BaseResponse>> updateProfile(@RequestBody @Valid Profile profile, @PathVariable String accountId) {
		try {
			UpdateAccountProfileCommand command =  UpdateAccountProfileCommand.builder()
															.id(accountId)
															.profile(profile)
															.build();
			//future
			return commandGateway.send(command)
			.thenApply(item -> 
			 new ResponseEntity<BaseResponse>(new BaseResponse("Account updated succesfully with ID "+accountId),
					HttpStatus.OK
					))
			.exceptionally(error -> new ResponseEntity<BaseResponse>(new BaseResponse("Error while updating Account with ID "+accountId),
						HttpStatus.INTERNAL_SERVER_ERROR
						))
			;
					}
			catch (Exception e) {
				return CompletableFuture.supplyAsync(() -> new ResponseEntity<BaseResponse>(new BaseResponse("Error while updating Account with ID "+accountId),
						HttpStatus.INTERNAL_SERVER_ERROR
						));
			}
		
	}
	
	@DeleteMapping("/{accountId}")
	@PreAuthorize("#oauth2.hasScope('write')")
	public CompletableFuture<ResponseEntity<BaseResponse>> deleteAccount(@PathVariable String accountId){
		try {
			DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(accountId);
			return commandGateway.send(deleteAccountCommand)
			.thenApply(item -> new ResponseEntity<BaseResponse>(new BaseResponse("Account Deleted succesfully with ID "+accountId),
					HttpStatus.NO_CONTENT
					))
			.exceptionally(error -> new ResponseEntity<BaseResponse>(new BaseResponse("Error while deleting Account with ID "+accountId),
					HttpStatus.INTERNAL_SERVER_ERROR
					))
			;
			
		}catch (Exception e) {
			return CompletableFuture.supplyAsync(()->  new ResponseEntity<BaseResponse>(new BaseResponse("Error while deleting Account with ID "+accountId),
					HttpStatus.INTERNAL_SERVER_ERROR
					));
		}
	}
	
	@PutMapping("/{accountId}/credit/{amount}")
	@PreAuthorize("#oauth2.hasScope('write')")
	public CompletableFuture<ResponseEntity<BaseResponse>> creditAmount(@PathVariable String accountId,@PathVariable String amount) {
		try {
			 CreditAccountCommand command = new CreditAccountCommand(accountId, Double.valueOf(amount));
			//future
			 return commandGateway.send(command)
					 .thenApply(item -> new ResponseEntity<BaseResponse>(new BaseResponse("Account Credited succesfully with ID "+accountId),
					HttpStatus.OK
					))
					 .exceptionally(error -> new ResponseEntity<BaseResponse>(new BaseResponse("Error while crediting Account with ID "+accountId),
						HttpStatus.INTERNAL_SERVER_ERROR
						))
					 ;
			
					}
			catch (Exception e) {
				return CompletableFuture.supplyAsync(()-> new ResponseEntity<BaseResponse>(new BaseResponse("Error while crediting Account with ID "+accountId),
						HttpStatus.INTERNAL_SERVER_ERROR
						));
			}
		
	}
	
	@PutMapping("/{accountId}/debit/{amount}")
	@PreAuthorize("#oauth2.hasScope('write')")
	public CompletableFuture<ResponseEntity<BaseResponse>> debitAmount(@PathVariable String accountId,@PathVariable String amount) {
		try {
			 DebitAccountCommand command = new DebitAccountCommand(accountId, Double.valueOf(amount));
			//future
			 //added for demonstartion, as we have error handling in comand handler if balance i
			return commandGateway.send(command)
				.thenApply(item -> new ResponseEntity<BaseResponse>(new BaseResponse("Account debited succesfully with ID "+accountId),
					HttpStatus.OK
					))
				.exceptionally(error -> new ResponseEntity<BaseResponse>(new BaseResponse("Error while debitingAccount with ID "+accountId),
						HttpStatus.INTERNAL_SERVER_ERROR
						))
			;
			
//			return new ResponseEntity<BaseResponse>(new BaseResponse("Account debited succesfully with ID "+accountId),
//					HttpStatus.OK
//					);
					}
			catch (Exception e) {
				return CompletableFuture.supplyAsync(() ->   new ResponseEntity<BaseResponse>(new BaseResponse("Error while debitingAccount with ID "+accountId),
						HttpStatus.INTERNAL_SERVER_ERROR
						));
			}
		
	}
}
