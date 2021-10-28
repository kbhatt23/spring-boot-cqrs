package com.learning.bankservice.handlers;

import com.learning.bankservice.events.CreateAccountEvent;
import com.learning.bankservice.events.DepositEvent;
import com.learning.bankservice.events.RemoveAccountEvent;
import com.learning.bankservice.events.UpdateAddressEvent;
import com.learning.bankservice.events.UpdateNameEvent;
import com.learning.bankservice.events.WithdrawEvent;

public interface BankAccountEventHandler {

	void handle(CreateAccountEvent createAccountEvent);

	void handle(DepositEvent depositEvent);
	
	void handle(WithdrawEvent withdrawEvent);
	
	void handle(RemoveAccountEvent removeAccountEvent);
	
	void handle(UpdateNameEvent updateNameEvent);
	
	void handle(UpdateAddressEvent updateAddressEvent);
}
