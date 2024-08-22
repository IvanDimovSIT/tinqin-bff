package com.tinqinacademy.bff.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.kafka.model.WordMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendWordMessage(WordMessage message) {
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            kafkaTemplate.send("words", messageJson);
        } catch (Exception e) {
            log.error(String.format("Error sending message: %s", e.getMessage()));
        }
    }
}
