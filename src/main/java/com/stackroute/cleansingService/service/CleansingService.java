package com.stackroute.cleansingService.service;


import com.stackroute.cleansingService.domain.Domain;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CleansingService {

    @Autowired
    KafkaTemplate<String, Domain> KafkaTemplate;

    @KafkaListener(topics = "producer", groupId = "sample-group", containerFactory = "kafkaListener")
    public void specialCharacterRemover(Domain domain) throws NullPointerException{
        String lemma="";
        String words=domain.getObject().getContent();

        Pattern pt = Pattern.compile("[^a-zA-Z0-9 ]");
        Matcher match = pt.matcher(words);
        while (match.find()) {
            String s = match.group();
            words = words.replaceAll("\\" + s, "");
        }
        words = Normalizer.normalize(words, Normalizer.Form.NFKD).trim();

        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

        CoreDocument coreDocument = new CoreDocument(words);

        stanfordCoreNLP.annotate(coreDocument);

        List<CoreLabel> coreLabelList = coreDocument.tokens();

        for(CoreLabel coreLabel : coreLabelList) {
            lemma = lemma+coreLabel.lemma()+" ";
        }
        domain.getObject().setContent(lemma);
             KafkaTemplate.send("consumer",domain);
    }
}
