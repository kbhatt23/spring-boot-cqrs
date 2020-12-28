package com.learning.cqrs.bank_service.handlers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cqrs.bank_service.dtos.BankAccountQueryResponse;
import com.learning.cqrs.bank_service.models.BankAccount;
import com.learning.cqrs.bank_service.queries.FindAccdountByIdQuery;
import com.learning.cqrs.bank_service.queries.FindAccountByfilterQuery;
import com.learning.cqrs.bank_service.queries.FindAllAccountsQuery;
import com.learning.cqrs.bank_service.repositories.BankRepository;

@Service
public class BankAccountQueryHandler {

	@Autowired
	private BankRepository repository;
	
	@QueryHandler
	public BankAccountQueryResponse handle(FindAllAccountsQuery query) {
		List<BankAccount> findAll = repository.findAll();
		return new BankAccountQueryResponse(findAll);
	}
	@QueryHandler
	public BankAccountQueryResponse handle(FindAccdountByIdQuery query) {
		Optional<BankAccount> findById = repository.findById(query.getId());
		return findById.map( item -> new BankAccountQueryResponse(Arrays.asList(item)))
					.orElse(new BankAccountQueryResponse());
	}
	@QueryHandler
	public BankAccountQueryResponse handle(FindAccountByfilterQuery query) {
		List<BankAccount> findByFilterRegex = repository.findByFilterRegex(query.getFilter());
		return new BankAccountQueryResponse(findByFilterRegex);
	}
}
