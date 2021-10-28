package com.learning.bankservice.handlers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.bankservice.dtos.BankQueryResponse;
import com.learning.bankservice.models.BankAccount;
import com.learning.bankservice.queries.FindAccountByIdQuery;
import com.learning.bankservice.queries.FindAccountBySearchQuery;
import com.learning.bankservice.queries.FindAllAccountsQuery;
import com.learning.bankservice.repositories.BankAccountRepository;

@Service
public class BankAccountQueryHandlerImpl implements BankAccountQueryHandler{

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@QueryHandler
	@Override
	public BankQueryResponse handle(FindAllAccountsQuery findAllAccountsQuery) {
		 List<BankAccount> findAll = bankAccountRepository.findAll();
		 return new BankQueryResponse(findAll);
	}
	
	@QueryHandler
	@Override
	public BankQueryResponse handle(FindAccountByIdQuery findAccountByIdQuery) {
		BankQueryResponse res = bankAccountRepository.findById(findAccountByIdQuery.getAccountId())
													.map(item -> new BankQueryResponse(Arrays.asList(item)))
													.orElse(new BankQueryResponse());
					;
		return res;
	}

	@QueryHandler
	@Override
	public BankQueryResponse handle(FindAccountBySearchQuery findAccountBySearchQuery) {
		return null;
	}

}
