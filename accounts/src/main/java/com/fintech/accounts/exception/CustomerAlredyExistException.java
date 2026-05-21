package com.fintech.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlredyExistException extends RuntimeException {
 
	public CustomerAlredyExistException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
}
