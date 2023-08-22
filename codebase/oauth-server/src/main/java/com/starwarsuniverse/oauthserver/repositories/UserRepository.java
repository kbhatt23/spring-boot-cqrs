package com.starwarsuniverse.oauthserver.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starwarsuniverse.oauthserver.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

//	@Query("{'$or' : [{'firstName': {$regex : ?0, $options: '1'}}, {'lastName': {$regex : ?0, $options: '1'}}, {'email': {$regex : ?0, $options: '1'}}, {'account.userName': {$regex : ?0, $options: '1'}}]}")
//    List<User> findByFilterRegex(String filter);
	
    Optional<User> findByEmail(String username);
}
