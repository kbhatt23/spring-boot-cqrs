package com.learning.cqrs.oauth_server.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.learning.cqrs.oauth_server.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

//	@Query("{'$or' : [{'firstName': {$regex : ?0, $options: '1'}}, {'lastName': {$regex : ?0, $options: '1'}}, {'email': {$regex : ?0, $options: '1'}}, {'account.userName': {$regex : ?0, $options: '1'}}]}")
//    List<User> findByFilterRegex(String filter);
	
	@Query("{'account.userName': ?0}")
    Optional<User> findByUsername(String username);
}
