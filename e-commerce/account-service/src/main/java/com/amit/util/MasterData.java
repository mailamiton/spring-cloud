package com.amit.util;

import com.amit.model.Account;
import com.amit.persist.IAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class MasterData implements CommandLineRunner {

    @Autowired
    IAccount accountRepo;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Master Data Init Started:: ");
        String[][] data = {
                {"mailamiton@gmail.com", "10000"},
                {"rakesh@gmail.com", "2000"},
                {"rohan@gmail.com", "100"},
                {"sohan@gmail.com", "0"}
        };
            Stream.of(data).forEach(array -> {
                try {
                    Account account = new Account(
                            array[0],
                            Integer.parseInt(array[1])
                    );
                    accountRepo.save(account);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
            accountRepo.findAll().forEach( account -> {
                        System.out.println(account.getCustomerId());
                    }
                );
    }
}
