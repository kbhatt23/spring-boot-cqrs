package com.learning.usersservice.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.learning.usersservice.models.User;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {
	@Query("{'$or' : [{'firstName': {$regex : ?0, $options: '1'}}, {'lastName': {$regex : ?0, $options: '1'}}, {'account.userName': {$regex : ?0, $options: '1'}}]}")
	List<User> findByFilterRegex(String text);
}
