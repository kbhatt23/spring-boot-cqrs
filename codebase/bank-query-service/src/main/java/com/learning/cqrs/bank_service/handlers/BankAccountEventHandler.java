package com.learning.cqrs.bank_service.handlers;

import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cqrs.bank_service.events.CreateAccountEvent;
import com.learning.cqrs.bank_service.events.CreditAccountEvent;
import com.learning.cqrs.bank_service.events.DebitAccountEvent;
import com.learning.cqrs.bank_service.events.DeleteAccountEvent;
import com.learning.cqrs.bank_service.events.UpdateAccountProfileEvent;
import com.learning.cqrs.bank_service.models.BankAccount;
import com.learning.cqrs.bank_service.repositories.BankRepository;

@Service
@ProcessingGroup("bank-group")
public class BankAccountEventHandler {

	@Autowired
	private BankRepository bankRepository;
	
	@EventHandler
	public void on(CreateAccountEvent event) {
		bankRepository.save(event.getBankAccount());
	}
	
	@EventHandler
	public void on(UpdateAccountProfileEvent event) {
		//copy and publish only the profile data
		Optional<BankAccount> existing = bankRepository.findById(event.getId());
		if(existing.isPresent()) {
			BankAccount bankAccount = existing.get();
			bankAccount.setProfile(event.getBankAccount());
			bankRepository.save(bankAccount);
		}
	}
	
	@EventHandler
	public void on(DeleteAccountEvent event) {
		bankRepository.deleteById(event.getId());
		}
	
	@EventHandler
	public void on(CreditAccountEvent event) {
		Optional<BankAccount> existing = bankRepository.findById(event.getId());
		if(existing.isPresent()) {
			BankAccount bankAccount = existing.get();
			bankAccount.setBalance(bankAccount.getBalance()+event.getAmount());
			bankRepository.save(bankAccount);
		}
		}
	
	@EventHandler
	public void on(DebitAccountEvent event) {

		Optional<BankAccount> existing = bankRepository.findById(event.getId());
		if(existing.isPresent()) {
			BankAccount bankAccount = existing.get();
			bankAccount.setBalance(bankAccount.getBalance()-event.getAmount());
			bankRepository.save(bankAccount);
		}
		
	}
}
