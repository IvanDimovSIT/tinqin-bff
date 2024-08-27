package com.tinqinacademy.bff.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic wordsTopic() {
        return TopicBuilder.name("words")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic deleteTopic() {
        return TopicBuilder.name("delete")
                .partitions(10)
                .replicas(1)
                .build();
    }

}
