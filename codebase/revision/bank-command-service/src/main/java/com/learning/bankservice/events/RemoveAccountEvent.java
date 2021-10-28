package com.learning.bankservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveAccountEvent {

	private String id;
	
	private String accountId;
}
