package com.fintech.accounts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Accounts extends BaseEntity{
	 @Column(name="customer_id")
	    private Long customerId;

	    @Column(name="account_number")
	    @Id // no need bank account as 1,2 ,3 so we write a UUID
	    private Long accountNumber;

	    @Column(name="account_type")
	    private String accountType;

	    @Column(name="branch_address")
	    private String branchAddress;

}
