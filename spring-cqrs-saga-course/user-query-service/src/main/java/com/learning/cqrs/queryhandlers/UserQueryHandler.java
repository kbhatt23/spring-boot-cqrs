package com.learning.cqrs.queryhandlers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cqrs.beans.UserDTO;
import com.learning.cqrs.entities.UserRepository;
import com.learning.cqrs.queries.FindUserByIDQuery;
import com.learning.cqrs.queries.FindUserPreferenceByID;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserQueryHandler {

	@Autowired
	private UserRepository userRepository;
	
	private static final Random RANDOM = new Random();
	
	private static final List<String> PREFERENCES = Arrays.asList("Fedex", "UPS" , "DTDC", "BlueDart");

	@QueryHandler
	public CompletableFuture<UserDTO> on(FindUserByIDQuery findUserByIDQuery){
		return CompletableFuture.supplyAsync(() -> { 
			String userId = findUserByIDQuery.getUserID();
			log.info("on: findUserByIDQuery called for id "+userId);
			return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("userId "+userId+" do not exist in D.B"));
			})
				.thenApply(userEntity -> {
					UserDTO userDto = new UserDTO();
					BeanUtils.copyProperties(userEntity, userDto); return userDto;
					});
				
	}
	
	//imagine taking preference from d.b
	@QueryHandler
	public CompletableFuture<String> on(FindUserPreferenceByID findUserPreferenceByID){
		return CompletableFuture.supplyAsync(() -> {
			String userId = findUserPreferenceByID.getUserID();
			int preferenceIdnex  = RANDOM.nextInt(PREFERENCES.size());
			String preference =  PREFERENCES.get(preferenceIdnex);
			
			log.info("on: findUserPreferenceByID preference for user: "+userId + " is: "+preference);
			return preference;
			
			//for demo returning null for testing saga at last stage
			//return null;
		});
		
	}
	
}
