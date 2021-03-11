package com.amit.controller;

import com.amit.exception.EntityNotFoundException;
import com.amit.exception.InSufficientInventoryException;
import com.amit.model.Product;
import com.amit.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/inventory")
public class InventoryController {

        @Autowired
        InventoryService inventoryService;

        @PostMapping("/getProducts")
        public List<Product> getProducts() throws EntityNotFoundException {
                log.
                        info("Get Product List");
                return inventoryService.getProducts();
        }

        @PostMapping("/updateInventory")
        public boolean updateInventory(@RequestBody Product product) throws InSufficientInventoryException {
                log.
                        info("Product :: {}" + product.getProductId() + " :: quantity :: {}" + product.getQuantity());
                return inventoryService.addRemoveProduct(product);
        }
}
