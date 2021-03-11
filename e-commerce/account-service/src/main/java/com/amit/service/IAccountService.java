package com.amit.service;

import com.amit.exception.EntityNotFoundException;
import com.amit.exception.InSufficientFundException;
import com.amit.model.Account;

import java.util.Optional;

public interface IAccountService {

    Account getAccountInfo(Account account) throws EntityNotFoundException;
    boolean withdrawAmount(Account account) throws InSufficientFundException;
}
