package com.fintech.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDTO {
	 @NotEmpty(message = "AccountNumber can not be a null or empty")
	  @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
	 private Long accountNumber;
	 @NotEmpty(message = "Account Type cant be null or Empty")
	private String accountType;
	 @NotEmpty(message = "BranchAddress cant be null or Empty")
	private String branchAddress;

}
