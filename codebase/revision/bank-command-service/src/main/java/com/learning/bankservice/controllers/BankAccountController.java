package com.learning.bankservice.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

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

import com.learning.bankservice.commands.CreateAccountCommand;
import com.learning.bankservice.commands.DepositCommand;
import com.learning.bankservice.commands.RemoveAccountCommand;
import com.learning.bankservice.commands.UpdateAddressCommand;
import com.learning.bankservice.commands.UpdateNameCommand;
import com.learning.bankservice.commands.WithdrawCommand;
import com.learning.bankservice.dtos.BankAccountResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bank")
@Slf4j
public class BankAccountController {

	@Autowired
	private CommandGateway commandGateway;

	@PostMapping
	public CompletableFuture<ResponseEntity<BankAccountResponse>> createAccount(
			@Valid @RequestBody CreateAccountCommand createAccountCommand) {
			createAccountCommand.setId(UUID.randomUUID().toString()); // random account number id
			return commandGateway.send(createAccountCommand)
						  .thenApply(res -> 
						  new ResponseEntity<BankAccountResponse>(new BankAccountResponse("account created succesully"),
									HttpStatus.CREATED)
						  )
						  .exceptionally(error -> {
							  log.error("createAccount: error occurred "+error);
							  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
						  }
							  )
						  ;
		}

	@PutMapping("/name")
	public CompletableFuture<ResponseEntity<BankAccountResponse>> updateName(@Valid @RequestBody UpdateNameCommand updateNameCommand) {
		
		return commandGateway.send(updateNameCommand)
				  .thenApply(res -> 
				  new ResponseEntity<BankAccountResponse>(new BankAccountResponse("name updated succesully"),
							HttpStatus.OK)
				  )
				  .exceptionally(error -> {
					  log.error("updateName: error occurred "+error);
					  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				  }
					  )
				  ;
		
	}

	@PutMapping("/address")
	public CompletableFuture<ResponseEntity<BankAccountResponse>> updateAddress(
			@Valid @RequestBody UpdateAddressCommand updateAddressCommand) {
		return commandGateway.send(updateAddressCommand)
				  .thenApply(res -> 
				  new ResponseEntity<BankAccountResponse>(new BankAccountResponse("address updated succesully"),
							HttpStatus.OK)
				  )
				  .exceptionally(error -> {
					  log.error("updateAddress: error occurred "+error);
					  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				  }
					  )
				  ;
		
		
	}

	@PutMapping("/deposit")
	public CompletableFuture<ResponseEntity<BankAccountResponse>> deposit(@Valid @RequestBody DepositCommand depositCommand) {
		return commandGateway.send(depositCommand)
				  .thenApply(res -> 
				  new ResponseEntity<BankAccountResponse>(new BankAccountResponse("deposit updated succesully"),
							HttpStatus.OK)
				  )
				  .exceptionally(error -> {
					  log.error("deposit: error occurred "+error);
					  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				  }
					  )
				  ;
		
		
	}

	@PutMapping("/withdraw")
	public CompletableFuture<ResponseEntity<BankAccountResponse>> withdraw(@Valid @RequestBody WithdrawCommand withdrawCommand) {
		return commandGateway.send(withdrawCommand)
				  .thenApply(res -> 
				  new ResponseEntity<BankAccountResponse>(new BankAccountResponse("withdraw updated succesully"),
							HttpStatus.OK)
				  )
				  .exceptionally(error -> {
					  log.error("withdraw: error occurred "+error);
					  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				  }
					  )
				  ;
		
	}

	@DeleteMapping("/{accoundID}")
	public CompletableFuture<ResponseEntity<BankAccountResponse>> delete(@PathVariable String accoundID) {

		return commandGateway.send(new RemoveAccountCommand(accoundID))
				  .thenApply(res -> 
				  new ResponseEntity<BankAccountResponse>(new BankAccountResponse("account removed succesully"),
							HttpStatus.NO_CONTENT)
				  )
				  .exceptionally(error -> {
					  log.error("delete: error occurred "+error);
					  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				  }
					  )
				  ;
		
	
		
	}

}
