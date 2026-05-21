package com.fintech.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
@NotEmpty(message = "name cant be null or empty")
@Size(min = 5, max = 30)
private String name;
@Email(message = "Email address cant be null or valid")
private String email;
@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
private String mobileNumber;
private AccountsDTO accountsDto;
}
