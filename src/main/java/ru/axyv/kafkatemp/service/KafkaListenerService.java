package ru.axyv.kafkatemp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import ru.axyv.kafkatemp.model.Msg;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class KafkaListenerService {
    @Autowired
    private KafkaTemplate<String, String> template;

    @KafkaListener(topics = "myTopic1", groupId = "group-raw-12345", clientIdPrefix = "raw")
//    @SendTo("myTopic-reply-2")
    public void listen(ConsumerRecord<?, Msg> cr) {
        log.info("RAW" + cr.toString());
//        return "Payload:"+cr.value().getPayload();
        template.send("myTopic-reply-2", "1", "Payload:"+cr.value().getPayload());
    }

//    @KafkaListener(topics = "myTopic-reply-2", groupId = "group-object")
    @KafkaListener(topics = "myTopic-reply-2", groupId = "group-object", clientIdPrefix = "reply")
    public void listen(String s, Acknowledgment ack) {
        log.info("Reply val: " + s);
        if(ThreadLocalRandom.current().nextBoolean())
            ack.acknowledge();
    }
}
