package com.fintech.loans.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoansAlredyExistsException extends RuntimeException{
	public LoansAlredyExistsException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

}
