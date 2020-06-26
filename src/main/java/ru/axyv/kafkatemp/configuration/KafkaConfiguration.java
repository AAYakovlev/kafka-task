package ru.axyv.kafkatemp.configuration;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import ru.axyv.kafkatemp.model.Msg;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {


//    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("Topic-1")
                .partitions(10)
//                .replicas(3)
                .compact()
                .build();
    }

//    @Bean
    public NewTopic myTopicReply() {
        return TopicBuilder.name("myTopic-reply-3")
//                .partitions(2)
//                .replicas(3)
//                .compact()
                .build();
    }

//    @Bean
    public ProducerFactory<String, Msg> producerFactory() {
        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return new DefaultKafkaProducerFactory<>(props);
    }

//    @Bean
    public KafkaTemplate<String, Msg> kafkaTemplate() {
        KafkaTemplate<String, Msg> template = new KafkaTemplate<String, Msg>(producerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
