package com.fintech.accounts.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fintech.accounts.Accounts;
import com.fintech.accounts.Customer;
import com.fintech.accounts.constants.AccountConstants;
import com.fintech.accounts.dto.AccountsDTO;
import com.fintech.accounts.dto.CustomerDTO;
import com.fintech.accounts.exception.CustomerAlredyExistException;
import com.fintech.accounts.exception.ResourceNotFoundException;
import com.fintech.accounts.mapper.AccountMapper;
import com.fintech.accounts.mapper.CustomerMapper;
import com.fintech.accounts.repository.AccountsRepository;
import com.fintech.accounts.repository.CustomerRepository;

import jakarta.validation.constraints.Pattern;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class AccountServiceImpl implements iAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	@Transactional
	public void createAccount(CustomerDTO customerdto) {
		System.out.println("===Saving Customer");
		System.out.println("Mobile" + customerdto.getMobileNumber());

		// TODO Auto-generated method stub
		Customer customer = CustomerMapper.mapToCustomer(customerdto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerdto.getMobileNumber());
		if (optionalCustomer.isPresent()) {
			throw new CustomerAlredyExistException(
					"customer alredy regsitered with given number" + customerdto.getMobileNumber());
		}
		customer.setCreatedAt(LocalDateTime.now());
		customer.setCreatedBy("Anynomous");
		Customer savedCustomer = customerRepository.save(customer);
		System.out.println("Saved! customer Id:" + savedCustomer.getCustomerId());
		// bank account by using name email mobileNumber of customer and save in account
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	public Accounts createNewAccount(Customer customer) {
		Accounts newAccounts = new Accounts();
		newAccounts.setCustomerId(customer.getCustomerId());
		long randomAccountNumber = 10000000L + new Random().nextInt(900000000);
		newAccounts.setAccountNumber(randomAccountNumber);
		newAccounts.setAccountType("SAVINGS");
		newAccounts.setBranchAddress(AccountConstants.ADDRESS);
		newAccounts.setCreatedAt(LocalDateTime.now());
		newAccounts.setCreatedBy("Anonymous");
		return newAccounts;
	}

	public ResponseEntity<CustomerDTO> fetchAccountDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(()->new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
		//CustomerDTO customerDto= CustomerMapper.mapToCustomer(customer, new cus)
		CustomerDTO customerdto=CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
		customerdto.setAccountsDto(
			    AccountMapper.mapToAccountsDto(accounts, new AccountsDTO())
			);
		return ResponseEntity.ok(customerdto);
	}

	@Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDTO customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDTO()));
        return customerDto;
    }

	@Override
	public boolean updateAccount(CustomerDTO customerdto) {
		// TODO Auto-generated method stub
		  boolean isUpdated = false;
	        AccountsDTO accountsDto = customerdto.getAccountsDto();
	        if(accountsDto !=null ){
	            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
	                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
	            );
	            AccountMapper.mapToAccounts(accountsDto, accounts);
	            accounts = accountsRepository.save(accounts);

	            Long customerId = accounts.getCustomerId();
	            Customer customer = customerRepository.findById(customerId).orElseThrow(
	                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
	            );
	            CustomerMapper.mapToCustomer(customerdto,customer);
	            customerRepository.save(customer);
	            isUpdated = true;
	        }
	        return  isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		// TODO Auto-generated method stub
		 Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
	        );
	        accountsRepository.deleteByCustomerId(customer.getCustomerId());
	        customerRepository.deleteById(customer.getCustomerId());
	        return true;
	    }
	

	
}
