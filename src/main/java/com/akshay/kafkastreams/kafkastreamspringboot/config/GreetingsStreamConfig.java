package com.akshay.kafkastreams.kafkastreamspringboot.config;

import com.akshay.kafkastreams.kafkastreamspringboot.topology.GreetingsStreamsTopology;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class GreetingsStreamConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Bean
    public NewTopic greetingsTopic() {
        return TopicBuilder
                .name(GreetingsStreamsTopology.GREETINGS)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic greetingsOutputTopic() {
        return TopicBuilder
                .name(GreetingsStreamsTopology.GREETINGS_OUTPUT)
                .partitions(2)
                .replicas(1)
                .build();
    }
}
