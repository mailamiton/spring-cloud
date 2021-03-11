package com.amit.service;

import com.amit.exception.EntityNotFoundException;
import com.amit.exception.InSufficientInventoryException;
import com.amit.model.Product;

import java.util.List;

public interface InventoryService {

    List<Product> getProducts() throws EntityNotFoundException;
    boolean addRemoveProduct(Product product) throws InSufficientInventoryException;
}
