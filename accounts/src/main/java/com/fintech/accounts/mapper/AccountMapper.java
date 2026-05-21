package com.fintech.accounts.mapper;

import com.fintech.accounts.Accounts;
import com.fintech.accounts.dto.AccountsDTO;

public class AccountMapper {
	public static AccountsDTO mapToAccountsDto(Accounts accounts, AccountsDTO accountdto) {
		accountdto.setAccountNumber(accounts.getAccountNumber());
		accountdto.setAccountType(accounts.getAccountType());
		accountdto.setBranchAddress(accounts.getBranchAddress());
		return accountdto;
	}
	public static Accounts mapToAccounts(AccountsDTO accountsdto,Accounts accounts) {
		accounts.setAccountNumber(accountsdto.getAccountNumber());
		accounts.setAccountType(accountsdto.getAccountType());
		accounts.setBranchAddress(accountsdto.getBranchAddress());
		return accounts;
		
	}
}
