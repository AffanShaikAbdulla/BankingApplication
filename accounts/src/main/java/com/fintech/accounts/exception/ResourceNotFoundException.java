package com.fintech.accounts.exception;

public class ResourceNotFoundException extends RuntimeException {
  
	public ResourceNotFoundException(String resourceName, String fieldName,String fieldValue) {
		// TODO Auto-generated constructor stub
		 super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
		
	}
}
