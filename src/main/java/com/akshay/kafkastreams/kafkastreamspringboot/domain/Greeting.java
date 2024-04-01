package com.akshay.kafkastreams.kafkastreamspringboot.domain;

import java.time.LocalDateTime;

public record Greeting(String message, LocalDateTime timeStamp) {
}
