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

    public RadioBaseStationConsumer(RadioBaseStationMessageRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
        topics = "radio-base-station-topic",
        groupId = "radio-consumer-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(RadioBaseStationMessage message) {
        logger.info("Consumed message: {}", message);
        repository.save(message);
    }
}
