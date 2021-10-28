package com.learning.bankservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//we can update name and address here
//rember we are using putmaping means complete object is updated
//whatever comes from postman will be kept as it is
//in patch mapping we merge data
@Data
@AllArgsConstructor
@NoArgsConstructor // for jackson
public class UpdateAddressEvent {

	private String id;

	private String address;
	
	private String accountId;

}
