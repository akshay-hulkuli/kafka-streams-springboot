package com.akshay.kafkastreams.kafkastreamspringboot.topology;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreetingsStreamsTopology {

    public static String GREETINGS = "greetings";
    public static String GREETINGS_OUTPUT = "greetings_output";

    @Autowired
    public void process(StreamsBuilder streamBuilder) {
        KStream<String, String> greetingsStream = streamBuilder
                .stream(GREETINGS, Consumed.with(Serdes.String(), Serdes.String()));

        KStream<String, String> modifiedStream = greetingsStream
                .mapValues((readOnlyKey, value) -> value.toUpperCase())
                .peek((key, value) -> {
                    log.info("Greetings Stream --> key: {}, value: {}", key, value);
                });

        modifiedStream.to(GREETINGS_OUTPUT, Produced.with(Serdes.String(), Serdes.String()));

    }

}
