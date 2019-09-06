package com.stackroute.kafkaservice.service;

import com.stackroute.kafkaservice.domain.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KafkaCleansingService {

    @Autowired
    KafkaTemplate<String,String> KafkaTemplate;

    @KafkaListener(topics = "producer", groupId = "sample-group",containerFactory = "kafkaListener")
    public void specialCharacterRemover(Domain domain){
        System.out.println("msg :"+domain);
        String words =domain.toString();
        Pattern pt = Pattern.compile("[^a-zA-Z0-9 ]");
        Matcher match = pt.matcher(words);
        while (match.find()) {
            String s = match.group();
            words = words.replaceAll("\\" + s, "");
        }
        words = Normalizer.normalize(words, Normalizer.Form.NFKD);
        List<String> items = Arrays.asList(words.split(" "));
        KafkaTemplate.send("consumer",words);
        System.out.println("words are :"+items);

    }
}