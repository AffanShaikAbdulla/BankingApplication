package com.fintech.accounts.service;

import com.fintech.accounts.dto.CustomerDTO;

public interface iAccountsService {
	
void createAccount(CustomerDTO customerdto);
CustomerDTO fetchAccount(String mobileNumber);
boolean updateAccount(CustomerDTO customerdto);
boolean deleteAccount(String mobileNumber);

}
