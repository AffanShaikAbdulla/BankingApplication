package com.fintech.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CardAlreadyExistsException extends RuntimeException{
	
	public CardAlreadyExistsException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}
}
