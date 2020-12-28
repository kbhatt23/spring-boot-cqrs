package com.learning.cqrs.bank_service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	//ignoring date of birth as of now
}
