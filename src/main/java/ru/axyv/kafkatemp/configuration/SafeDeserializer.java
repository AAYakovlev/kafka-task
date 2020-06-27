package ru.axyv.kafkatemp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Slf4j
public class SafeDeserializer<T> extends JsonDeserializer<T> {
    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return super.deserialize(topic, data);
        } catch (Exception e) {
            log.warn("Failed deserialization of {}", data, e);
            return null;
        }
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            return super.deserialize(topic, headers, data);
        } catch (Exception e) {
            log.warn("Failed deserialization of {}", data, e);
            return null;
        }
    }
}
