package com.tus.radiobasestationconsumer.service;

import com.tus.radiobasestationconsumer.model.RadioBaseStationMessage;
import com.tus.radiobasestationconsumer.repository.RadioBaseStationMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RadioBaseStationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RadioBaseStationConsumer.class);

    private final RadioBaseStationMessageRepository repository;

    // inject a JPA repository for saving RadioBaseStationMessage objects to the database
    public RadioBaseStationConsumer(RadioBaseStationMessageRepository repository) {
        this.repository = repository;
    }

    // method will be automatically called whenever a message is received from Kafka topic "radio-base-station-topic"
    @KafkaListener(
        topics = "radio-base-station-topic",
        groupId = "radio-consumer-group",
        containerFactory = "kafkaListenerContainerFactory" // uses JSON deserialiser
    )
    public void consume(RadioBaseStationMessage message) {
        logger.info("Consumed message: {}", message); // log out notification that message is consumed
        repository.save(message); // save the message to database using the injected JPA repository .save()
    }
}
