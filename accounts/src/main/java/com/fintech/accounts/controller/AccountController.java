package com.fintech.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.accounts.constants.AccountConstants;
import com.fintech.accounts.dto.CustomerDTO;
import com.fintech.accounts.dto.ResponseDTO;
import com.fintech.accounts.service.iAccountsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountController {
	private iAccountsService accountsService;
	
	public AccountController(iAccountsService accountsService) {
		this.accountsService = accountsService;
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerdto) {
		System.out.println("validation passed creating account for "+customerdto.getMobileNumber());
		accountsService.createAccount(customerdto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDTO(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
	}
	 @GetMapping("/fetch")
	    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam
	                                                               @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
	                                                               String mobileNumber) {
	        CustomerDTO customerDto = accountsService.fetchAccount(mobileNumber);
	        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	    }
	 @DeleteMapping("/delete")
	 public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam
			 @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
     String mobileNumber){
		 boolean isDeleted = accountsService.deleteAccount(mobileNumber);
	        if(isDeleted) {
	            return ResponseEntity
	                    .status(HttpStatus.OK)
	                    .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
	        }else{
	            return ResponseEntity
	                    .status(HttpStatus.EXPECTATION_FAILED)
	                    .body(new ResponseDTO(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
	        } 
	 }
}
