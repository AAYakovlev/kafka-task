package ru.axyv.kafkatemp.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.axyv.kafkatemp.model.Payment;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final AnalyticsService analyticsService;

//    @Autowired
//    private KafkaTemplate<String, String> template;

//    @KafkaListener(topics = "myTopic1", groupId = "group-raw-12345", clientIdPrefix = "raw")
////    @SendTo("myTopic-reply-2")
//    public void listen(ConsumerRecord<?, Msg> cr) {
//        log.info("RAW" + cr.toString());
////        return "Payload:"+cr.value().getPayload();
//        template.send("myTopic-reply-2", "1", "Payload:"+cr.value().getPayload());
//    }
//
////    @KafkaListener(topics = "myTopic-reply-2", groupId = "group-object")
//    @KafkaListener(topics = "myTopic-reply-2", groupId = "group-object", clientIdPrefix = "reply")
//    public void listen(String s, Acknowledgment ack) {
//        log.info("Reply val: " + s);
//        if(ThreadLocalRandom.current().nextBoolean())
//            ack.acknowledge();
//    }

    @KafkaListener(topics = "RAW_PAYMENTS", groupId = "group1")
    public void listen(Payment payment, Acknowledgment ack) {
        log.debug("Reading payment: " + payment);
        if (payment != null)
            analyticsService.addPayment(payment);
        ack.acknowledge();
    }
}
