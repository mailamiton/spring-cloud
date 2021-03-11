package com.amit.persist;

import com.amit.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IProduct extends CrudRepository<Product, String> {
    Optional<Product> findByProductId(String productId);
}
