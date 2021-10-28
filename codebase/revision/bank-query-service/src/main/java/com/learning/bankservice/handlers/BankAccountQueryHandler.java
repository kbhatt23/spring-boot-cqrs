package com.learning.bankservice.handlers;

import com.learning.bankservice.dtos.BankQueryResponse;
import com.learning.bankservice.queries.FindAccountByIdQuery;
import com.learning.bankservice.queries.FindAccountBySearchQuery;
import com.learning.bankservice.queries.FindAllAccountsQuery;

public interface BankAccountQueryHandler {

	BankQueryResponse handle(FindAllAccountsQuery findAllAccountsQuery);
	
	BankQueryResponse handle(FindAccountByIdQuery findAccountByIdQuery);
	
	BankQueryResponse handle(FindAccountBySearchQuery findAccountBySearchQuery);
}
