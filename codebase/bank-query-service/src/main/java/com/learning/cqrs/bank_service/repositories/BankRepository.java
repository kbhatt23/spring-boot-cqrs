package com.learning.cqrs.bank_service.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.learning.cqrs.bank_service.models.BankAccount;

@Repository
public interface BankRepository extends MongoRepository<BankAccount, String>{

	@Query("{'$or' : [{'profile.firstName': {$regex : ?0, $options: '1'}}, {'profile.lastName': {$regex : ?0, $options: '1'}}, {'profile.email': {$regex : ?0, $options: '1'}}]}")
    List<BankAccount> findByFilterRegex(String filter);
}
