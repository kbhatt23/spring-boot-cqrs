package com.learning.cqrs.bank_service.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

	@NotEmpty(message = "first name can not be empty")
	private String firstName;
	
	@NotEmpty(message = "last name can not be empty")
	private String lastName;
	
	@Email(message = "Kindly provide valid email")
	private String email;
	
	//ignoring date of birth as of now
}
