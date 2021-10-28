package com.learning.bankservice.dtos;

import java.util.List;

import com.learning.bankservice.models.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankQueryResponse {

	private List<BankAccount> accounts;
}
