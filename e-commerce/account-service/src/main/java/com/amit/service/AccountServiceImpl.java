package com.amit.service;

import com.amit.exception.EntityNotFoundException;
import com.amit.exception.InSufficientFundException;
import com.amit.model.Account;
import com.amit.persist.IAccount;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements  IAccountService{

    @Autowired
    IAccount accountRepo;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private  String exchange;

    @Value("${spring.rabbitmq.queue}")
    private  String queue;

    @Value("${spring.rabbitmq.routingkey}")
    private  String routingkey;

    @Override
    public Account getAccountInfo(Account account) throws EntityNotFoundException {
        return  accountRepo.findByCustomerId(account.getCustomerId()).orElseThrow(()->
        new EntityNotFoundException(AccountServiceImpl.class, "Customer Id", account.getCustomerId())
        );
    }

    @Override
    public boolean withdrawAmount(Account account) throws InSufficientFundException {
        Optional<Account> optionalAccount = accountRepo.findByCustomerId(account.getCustomerId());
        optionalAccount.ifPresent(accnt -> {
            if(accnt.getBalance() > account.getBalance()) {
                accnt.setBalance(accnt.getBalance() - account.getBalance());
                //save updated balance
                accountRepo.save(accnt);
                rabbitTemplate.convertAndSend(exchange, routingkey, accnt);
            }else {
                throw new InSufficientFundException(AccountServiceImpl.class, "Amount", Integer.toString(account.getBalance()));
            }
        });

        return true;
    }
}
