package com.akshay.kafkastreams.kafkastreamspringboot.topology;

import com.akshay.kafkastreams.kafkastreamspringboot.domain.Greeting;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreetingsStreamsTopology {

    private final ObjectMapper objectMapper;

    public GreetingsStreamsTopology(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static String GREETINGS = "greetings";
    public static String GREETINGS_OUTPUT = "greetings_output";

    @Autowired
    public void process(StreamsBuilder streamBuilder) {
        KStream<String, Greeting> greetingsStream = streamBuilder
                .stream(GREETINGS, Consumed.with(Serdes.String(), new JsonSerde<>(Greeting.class, objectMapper)));

        KStream<String, Greeting> modifiedStream = greetingsStream
                .mapValues((readOnlyKey, value) -> new Greeting(value.message().toUpperCase(), value.timeStamp()))
                .peek((key, value) -> {
                    log.info("Greetings Stream --> key: {}, value: {}", key, value);
                });

        modifiedStream.to(GREETINGS_OUTPUT, Produced.with(Serdes.String(), new JsonSerde<>(Greeting.class, objectMapper)));

    }

}
