package com.learning.bankservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import com.learning.bankservice.models.BankAccount;

@Service
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
	@Query("{'$or' : [{'firstName': {$regex : ?0, $options: '1'}}, {'lastName': {$regex : ?0, $options: '1'}}]}")
	List<BankAccount> findByFilterRegex(String text);
}
