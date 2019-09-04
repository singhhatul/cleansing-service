package com.stackroute.kafkaservice.service;

import com.stackroute.kafkaservice.domain.Domain;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "test1", groupId = "sample-group",containerFactory = "kafkaListener")
    public void consume(Domain domain) throws Exception{
        System.out.println("Consumed Message :"+ domain);
    }
}