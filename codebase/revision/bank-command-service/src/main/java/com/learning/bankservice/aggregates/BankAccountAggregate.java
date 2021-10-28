package com.learning.bankservice.aggregates;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.learning.bankservice.commands.CreateAccountCommand;
import com.learning.bankservice.commands.DepositCommand;
import com.learning.bankservice.commands.RemoveAccountCommand;
import com.learning.bankservice.commands.UpdateAddressCommand;
import com.learning.bankservice.commands.UpdateNameCommand;
import com.learning.bankservice.commands.WithdrawCommand;
import com.learning.bankservice.events.CreateAccountEvent;
import com.learning.bankservice.events.DepositEvent;
import com.learning.bankservice.events.RemoveAccountEvent;
import com.learning.bankservice.events.UpdateAddressEvent;
import com.learning.bankservice.events.UpdateNameEvent;
import com.learning.bankservice.events.WithdrawEvent;
import com.learning.bankservice.models.BankAccount;

import lombok.extern.slf4j.Slf4j;

//an aggregate contains entities whohc stat cahnges and we must monitor and manage ACID for that entity group
@Aggregate
@Slf4j
public class BankAccountAggregate {
	
	@AggregateIdentifier
	private String id;
	
	//we muse keep model/entity group in aggregate to monitor its state change
	private BankAccount bankAccount;
	
	public BankAccountAggregate() {
	}
	
	@CommandHandler
	public BankAccountAggregate(CreateAccountCommand createAccountCommand) {
		log.info("createAccountCommand called for command "+createAccountCommand);
		
		//command aggreaget id will be passed by controller
		CreateAccountEvent createAccountEvent = new CreateAccountEvent(createAccountCommand.getId(), createAccountCommand.getFirstName(), createAccountCommand.getLastName(),
				createAccountCommand.getInitialAmount(), createAccountCommand.getAddress());
		
		//sae to event source and if sucess push the event to event bus
		AggregateLifecycle.apply(createAccountEvent);
	}
	
	@EventSourcingHandler
	public void on(CreateAccountEvent createAccountEvent) {
		log.info("createAccountEvent called for event "+createAccountEvent);
		this.id = createAccountEvent.getId();
		this.bankAccount = new BankAccount(createAccountEvent.getId(), createAccountEvent.getFirstName(), createAccountEvent.getLastName(),
				createAccountEvent.getInitialAmount(), createAccountEvent.getAddress());
	}
	
	@CommandHandler
	public void handle(UpdateNameCommand updateNameCommand) {
		log.info("updateNameCommand called for command "+updateNameCommand);
		
		UpdateNameEvent updateNameEvent = new UpdateNameEvent(UUID.randomUUID().toString(), updateNameCommand.getFirstName(), updateNameCommand.getLastName(),
				updateNameCommand.getId()); // aggregate id should be bank account id
		
		AggregateLifecycle.apply(updateNameEvent);
		
	}
	
	@EventSourcingHandler
	public void on(UpdateNameEvent updateNameEvent) {
		log.info("updateNameEvent called for event "+updateNameEvent);
		this.bankAccount.setFirstName(updateNameEvent.getFirstName());
		this.bankAccount.setLastName(updateNameEvent.getLastName());
	}
	
	@CommandHandler
	public void handle(UpdateAddressCommand updateAddressCommand) {
		log.info("updateAddressCommand called for command "+updateAddressCommand);
		
		UpdateAddressEvent updateAddressEvent = new UpdateAddressEvent(UUID.randomUUID().toString(), updateAddressCommand.getAddress(), 
				updateAddressCommand.getId()
				);
		AggregateLifecycle.apply(updateAddressEvent);
		
	}
	
	@EventSourcingHandler
	public void on(UpdateAddressEvent updateAddressEvent) {
		log.info("updateAddressEvent called for event "+updateAddressEvent);
		this.bankAccount.setAddress(updateAddressEvent.getAddress());
	}
	
	@CommandHandler
	public void handle(DepositCommand depositCommand) {
		log.info("depositCommand called for command "+depositCommand);
		
		DepositEvent depositEvent = new DepositEvent(UUID.randomUUID().toString(), depositCommand.getAmount(), depositCommand.getId());
		
		AggregateLifecycle.apply(depositEvent);
		
	}
	
	@EventSourcingHandler
	public void on(DepositEvent depositEvent) {
		log.info("depositEvent called for event "+depositEvent);
		this.bankAccount.deposit(depositEvent.getAmount());
	}
	
	@CommandHandler
	public void handle(WithdrawCommand withdrawCommand) {
		log.info("withdrawCommand called for command "+withdrawCommand);
		
		//validation
		if(withdrawCommand.getAmount() > this.bankAccount.getAmount()) {
			throw new IllegalArgumentException("Amount withdrawn requested is greater than balance for command "+withdrawCommand);
		}
		
		WithdrawEvent withdrawEvent = new WithdrawEvent(UUID.randomUUID().toString(), withdrawCommand.getAmount(), withdrawCommand.getId());
		
		AggregateLifecycle.apply(withdrawEvent);
		
	}
	
	@EventSourcingHandler
	public void on(WithdrawEvent withdrawEvent) {
		log.info("withdrawEvent called for event "+withdrawEvent);
		this.bankAccount.withdraw(withdrawEvent.getAmount());
	}
	
	@CommandHandler
	public void handle(RemoveAccountCommand removeAccountCommand) {
		log.info("removeAccountCommand called for command "+removeAccountCommand);
		
		//remmeber in case of wrong account id an event wont be even called
		//same with all other hadnle method wrong id means aggragte id do not exist and hence wont call any method
		RemoveAccountEvent removeAccountEvent = new RemoveAccountEvent( UUID.randomUUID().toString(),removeAccountCommand.getId());
		AggregateLifecycle.apply(removeAccountEvent);
	}
	
	@EventSourcingHandler
	public void on(RemoveAccountEvent removeAccountEvent) {
		log.info("removeAccountEvent called for event "+removeAccountEvent);
		AggregateLifecycle.markDeleted();
		//remember even in future same id can not be used to create new object
	}
}
