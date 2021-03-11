package com.amit.util;

import com.amit.model.Product;
import com.amit.persist.IProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class MasterData implements CommandLineRunner {

    @Autowired
    IProduct prodRepo;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Master Data Init Started:: ");
        String[][] data = {
                {"Prod1","Product One", "100"},
                {"Prod2","Product two", "200"},
                {"Prod3","Product three",  "10"},
                {"Prod4","Product Four",  "0"}
        };
            Stream.of(data).forEach(array -> {
                try {
                    Product account = new Product(
                            array[0],
                            array[1],
                            Integer.parseInt(array[2])
                    );
                    prodRepo.save(account);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        prodRepo.findAll().forEach( prod -> {
                        System.out.println(prod.getName());
                    }
                );
    }
}
