package com.learning.cqrs.bank_service.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindAccdountByIdQuery {

	private String id;
}
