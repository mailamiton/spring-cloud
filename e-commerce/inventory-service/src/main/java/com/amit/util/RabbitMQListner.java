package com.amit.util;

import com.amit.model.Account;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListner implements MessageListener {

    public void onMessage(Message message) {
        System.out.println("Consuming Message - " + new String(message.getBody()));
    }

    @RabbitListener(queues = "account_queue")
    public void receiveMessage(Account message) {
        System.out.println("Received Message:" + message.toString());
        System.out.println();
    }

}