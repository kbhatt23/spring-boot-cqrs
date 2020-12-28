package com.learning.cqrs.bank_service.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.learning.cqrs.bank_service.commands.CreateAccountCommand;
import com.learning.cqrs.bank_service.commands.CreditAccountCommand;
import com.learning.cqrs.bank_service.commands.DebitAccountCommand;
import com.learning.cqrs.bank_service.commands.DeleteAccountCommand;
import com.learning.cqrs.bank_service.commands.UpdateAccountProfileCommand;
import com.learning.cqrs.bank_service.events.CreateAccountEvent;
import com.learning.cqrs.bank_service.events.CreditAccountEvent;
import com.learning.cqrs.bank_service.events.DebitAccountEvent;
import com.learning.cqrs.bank_service.events.DeleteAccountEvent;
import com.learning.cqrs.bank_service.events.UpdateAccountProfileEvent;
import com.learning.cqrs.bank_service.models.BankAccount;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class BankAccountAggregate {
	@AggregateIdentifier
	private String id;
	
	private BankAccount bankAccount;
	
	@CommandHandler
	public BankAccountAggregate(CreateAccountCommand command) {
		BankAccount account = command.getBankAccount();
		//not useful now , as we have handled in @valid annotation for request body
//		if (account.getBalance() < 0) {
//			throw new IllegalArgumentException(
//					"Can not Create account with negative balance , passed value " + account.getBalance());
//		}
		account.setId(command.getId());
		
		CreateAccountEvent createAccountEvent = CreateAccountEvent.builder()
					.id(command.getId())
					.bankAccount(account)
					.build();
		
		 AggregateLifecycle.apply(createAccountEvent);
	}
	
	@CommandHandler
	public void handle(UpdateAccountProfileCommand command) {
		UpdateAccountProfileEvent updateProfileEvent = UpdateAccountProfileEvent.builder()
				.id(command.getId())
				.bankAccount(command.getProfile())
				.build();
		 AggregateLifecycle.apply(updateProfileEvent);
	
	}
	
	@CommandHandler
	public void handle(CreditAccountCommand command) {
		//could have added a max limit of credit in one transaction
		CreditAccountEvent creditAccountCommand = CreditAccountEvent.builder()
															.amount(command.getAmount())
															.id(command.getId()).build();
		 AggregateLifecycle.apply(creditAccountCommand);
	}
	
	@CommandHandler
	public void handle(DebitAccountCommand command) {
		if((bankAccount.getBalance() - command.getAmount()) < 0)
			throw new IllegalArgumentException(
					"Can not Debit more than balance , current balance " + bankAccount.getBalance());
			
		DebitAccountEvent debitAccountCommand = DebitAccountEvent.builder()
															.amount(command.getAmount())
															.id(command.getId()).build();
		 AggregateLifecycle.apply(debitAccountCommand);
	}
	
	@CommandHandler
	public void handle(DeleteAccountCommand command) {
		DeleteAccountEvent deleteAccountEvent = new DeleteAccountEvent();
		deleteAccountEvent.setId(command.getId());
		AggregateLifecycle.apply(deleteAccountEvent);
	}
	
	@EventSourcingHandler
	public void on(CreateAccountEvent event) {
		this.id = event.getId();
		this.bankAccount = event.getBankAccount();
	}

	@EventSourcingHandler
	public void on(UpdateAccountProfileEvent event) {
		this.bankAccount.setProfile(event.getBankAccount());
	}

	@EventSourcingHandler
	public void on(DeleteAccountEvent event) {
		AggregateLifecycle.markDeleted();
	}

	@EventSourcingHandler
	public void on(CreditAccountEvent event) {
		this.bankAccount.setBalance(this.bankAccount.getBalance() + event.getAmount());
	}

	@EventSourcingHandler
	public void on(DebitAccountEvent event) {
		this.bankAccount.setBalance(this.bankAccount.getBalance() - event.getAmount());
	}
	
}
