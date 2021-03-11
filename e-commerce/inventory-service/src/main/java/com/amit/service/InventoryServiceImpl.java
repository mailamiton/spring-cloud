package com.amit.service;

import com.amit.exception.EntityNotFoundException;
import com.amit.exception.InSufficientInventoryException;
import com.amit.model.Product;
import com.amit.persist.IProduct;
import com.amit.util.Receiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    IProduct productRepo;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Receiver receiver;

    @Value("${spring.rabbitmq.exchange}")
    private  String exchange;

    @Value("${spring.rabbitmq.queue}")
    private  String queue;

    @Value("${spring.rabbitmq.routingkey}")
    private  String routingkey;

    @Override
    public List<Product> getProducts() throws EntityNotFoundException {
        return (List) productRepo.findAll();
    }

    @Override
    public boolean addRemoveProduct(Product product) throws InSufficientInventoryException {
        Optional<Product> optionalProduct = productRepo.findByProductId(product.getProductId());
        optionalProduct.ifPresent(prod -> {
            if(prod.getQuantity() > product.getQuantity()) {
                prod.setQuantity(prod.getQuantity() - product.getQuantity());
                //save updated balance
                productRepo.save(prod);
                rabbitTemplate.convertAndSend(exchange, routingkey, prod);

            }else {
                throw new InSufficientInventoryException(InventoryServiceImpl.class, "Quantity", Integer.toString(product.getQuantity()));
            }
        });

        return true;
    }


}
