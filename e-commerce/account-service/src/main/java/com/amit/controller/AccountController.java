package com.amit.controller;

import com.amit.exception.EntityNotFoundException;
import com.amit.exception.InSufficientFundException;
import com.amit.model.Account;
import com.amit.persist.IAccount;
import com.amit.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
@RequestMapping(path = "/account")
public class AccountController {

        @Autowired
        IAccountService accountService;

        @PostMapping("/getAccountDetailsByCustomerId")
        public Account getAccountDetailsByCustomerId(@RequestBody Account account) throws EntityNotFoundException {
                log.
                        info("Get Account Details For Customer Id{}", account.getCustomerId());
                return accountService.getAccountInfo(account);
        }

        @PostMapping("/withdraw")
        public boolean withdraw(@RequestBody Account account) throws InSufficientFundException {
                log.
                        info("Customer :: {}" + account.getCustomerId() + " :: withdraw Amount :: {}" + account.getBalance());
                return accountService.withdrawAmount(account);
        }
}
