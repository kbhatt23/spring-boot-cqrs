package com.learning.bankservice.handlers;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.learning.bankservice.events.CreateAccountEvent;
import com.learning.bankservice.events.DepositEvent;
import com.learning.bankservice.events.RemoveAccountEvent;
import com.learning.bankservice.events.UpdateAddressEvent;
import com.learning.bankservice.events.UpdateNameEvent;
import com.learning.bankservice.events.WithdrawEvent;
import com.learning.bankservice.models.BankAccount;
import com.learning.bankservice.repositories.BankAccountRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ProcessingGroup("bank-accounts")
public class BankAccountEventHandlerImpl implements BankAccountEventHandler {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;

	@EventHandler
	@Override
	public void handle(CreateAccountEvent createAccountEvent) {

		if(createAccountEvent != null && !StringUtils.isEmpty(createAccountEvent.getId())) {
			log.info("creating account for event "+createAccountEvent);
			BankAccount bankAccount = new BankAccount(createAccountEvent.getId(), createAccountEvent.getFirstName(), createAccountEvent.getLastName(), 
					createAccountEvent.getInitialAmount(), createAccountEvent.getAddress());
			bankAccountRepository.save(bankAccount);
			
		}else {
			log.error("account can not be created for event "+createAccountEvent);
		}
	}

	@EventHandler
	@Override
	public void handle(DepositEvent depositEvent) {
		//always event will have a valid id
		//no way bad id exist
		if(depositEvent != null && !StringUtils.isEmpty(depositEvent.getId()) && !StringUtils.isEmpty(depositEvent.getAccountId()) ) {
			log.info("depositing account for event "+depositEvent);
			bankAccountRepository.findById(depositEvent.getAccountId())
								.map(account -> {
									account.deposit(depositEvent.getAmount());
									return account;
								})
								.map(bankAccountRepository::save)
								.orElseThrow(() ->  new IllegalStateException("account can not be deposited for event "+depositEvent));
								
		}else {
			log.error("account can not be deposited for event "+depositEvent);
		}
	}

	@EventHandler
	@Override
	public void handle(WithdrawEvent withdrawEvent) {

		//always event will have a valid id
		//no way bad id exist
		if(withdrawEvent != null && !StringUtils.isEmpty(withdrawEvent.getId()) && !StringUtils.isEmpty(withdrawEvent.getAccountId()) ) {
			log.info("withdrawing account for event "+withdrawEvent);
			bankAccountRepository.findById(withdrawEvent.getAccountId())
								.map(account -> {
									account.withdraw(withdrawEvent.getAmount());
									return account;
								})
								.map(bankAccountRepository::save)
								.orElseThrow(() ->  new IllegalStateException("account can not be withdrawn for event "+withdrawEvent));
								
		}else {
			log.error("account can not be withdrawn for event "+withdrawEvent);
		}
	
	}

	@EventHandler
	@Override
	public void handle(RemoveAccountEvent removeAccountEvent) {
		if(removeAccountEvent != null && !StringUtils.isEmpty(removeAccountEvent.getId()) && !StringUtils.isEmpty(removeAccountEvent.getAccountId())) {
			log.info("removing account for event "+removeAccountEvent);
			bankAccountRepository.deleteById(removeAccountEvent.getAccountId());
		}else {
			log.error("account can not be deleted for event "+removeAccountEvent);
		}
	}

	@EventHandler
	@Override
	public void handle(UpdateNameEvent updateNameEvent) {
		if(updateNameEvent != null && !StringUtils.isEmpty(updateNameEvent.getId()) && !StringUtils.isEmpty(updateNameEvent.getAccountId()) ) {
			log.info("updaing name for event "+updateNameEvent);
			bankAccountRepository.findById(updateNameEvent.getAccountId())
								.map(account -> {
									account.setFirstName(updateNameEvent.getFirstName());
									account.setLastName(updateNameEvent.getLastName());
									return account;
								})
								.map(bankAccountRepository::save)
								.orElseThrow(() ->  new IllegalStateException("name can not be updated for event "+updateNameEvent));
								
		}else {
			log.error("name can not be updated for event "+updateNameEvent);
		}
	}

	@EventHandler
	@Override
	public void handle(UpdateAddressEvent updateAddressEvent) {
		if(updateAddressEvent != null && !StringUtils.isEmpty(updateAddressEvent.getId()) && !StringUtils.isEmpty(updateAddressEvent.getAccountId()) ) {
			log.info("updaing address for event "+updateAddressEvent);
			bankAccountRepository.findById(updateAddressEvent.getAccountId())
								.map(account -> {
									account.setAddress(updateAddressEvent.getAddress());
									return account;
								})
								.map(bankAccountRepository::save)
								.orElseThrow(() ->  new IllegalStateException("address can not be updated for event "+updateAddressEvent));
								
		}else {
			log.error("address can not be updated for event "+updateAddressEvent);
		}
	}

}
