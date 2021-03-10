package com.amit.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

public class RabbitMqConfig {


    public static final String QUEUE = "account_queue";
    public static final String EXCHANGE = "account_exchange";
    public static final String ROUTING_KEY = "account_routingKey";


    @Value("${spring.rabbitmq.exchange}")
    private  String exchange;

    @Value("${spring.rabbitmq.queue}")
    private  String queue;

    @Value("${spring.rabbitmq.routingkey}")
    private  String routingkey;


    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
