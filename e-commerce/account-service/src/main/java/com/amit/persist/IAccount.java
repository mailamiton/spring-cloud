package com.amit.persist;

import com.amit.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IAccount extends CrudRepository<Account, String> {
    Optional<Account> findByCustomerId(String customerId);
}
