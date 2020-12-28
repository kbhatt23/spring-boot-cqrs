package com.learning.cqrs.bank_service.dtos;

import java.util.List;

import com.learning.cqrs.bank_service.models.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountQueryResponse {

	private List<BankAccount> accounts;
}
