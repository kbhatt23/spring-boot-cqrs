package com.learning.cqrs.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindOrderByUserId {

	private String orderId;
}
