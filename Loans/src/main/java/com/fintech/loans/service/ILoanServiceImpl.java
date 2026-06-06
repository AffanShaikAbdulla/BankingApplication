package com.fintech.loans.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.loans.Exception.LoansAlredyExistsException;
import com.fintech.loans.Exception.ResourceNotFoundException;
import com.fintech.loans.constants.LoanConstants;
import com.fintech.loans.dto.LoansDto;
import com.fintech.loans.entity.Loans;

import com.fintech.loans.mapper.LoansMapper;
import com.fintech.loans.repository.LoansRepository;

@Service
public class ILoanServiceImpl implements ILoanService {

    @Autowired
    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoans =
                loansRepository.findByMobileNumber(mobileNumber);

        if (optionalLoans.isPresent()) {

            throw new LoansAlredyExistsException(
                    "Loan already exists with mobile number "
                            + mobileNumber
            );
        }

        loansRepository.save(createNewLoan(mobileNumber));
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {

        Loans loans = loansRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "mobileNumber",
                                mobileNumber
                        ));

        return LoansMapper.mapToLoansDto(
                loans,
                new LoansDto()
        );
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {

        Loans loans = loansRepository
                .findByLoanNumber(loansDto.getLoanNumber()) 
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "loanNumber",
                                loansDto.getLoanNumber()
                        ));

        LoansMapper.mapToLoans(loansDto, loans);

        loansRepository.save(loans);

        return true;
    }

    private Loans createNewLoan(String mobileNumber) {

        Loans newLoan = new Loans();

        long randomLoanNumber =
                100000000000L + new Random().nextInt(900000000);

        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);

        return newLoan;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan",
                                "mobileNumber",
                                mobileNumber
                        ));

        loansRepository.deleteById(loans.getLoanId());

        return true;
    }
}