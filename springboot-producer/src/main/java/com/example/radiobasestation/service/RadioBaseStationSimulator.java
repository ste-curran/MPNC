package com.example.radiobasestation.service;

import com.example.radiobasestation.model.BaseStationConfig;
import com.example.radiobasestation.model.RadioBaseStationMessage;
import com.example.radiobasestation.repository.BaseStationConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

// Inject service for use in Spring app
@Service
public class RadioBaseStationSimulator {

    private static final Logger logger = LoggerFactory.getLogger(RadioBaseStationSimulator.class);

    private static final String TOPIC = "radio-base-station-topic";

    // Inject JPA repo to allow configuration of Base Station e.g. enable / disable
    @Autowired
    private BaseStationConfigRepository configRepository;

    // Injects a Kafka template to publish Base Station messages to a Kafka topic
    @Autowired
    private KafkaTemplate<String, RadioBaseStationMessage> kafkaTemplate;

    private final Random random = new Random();

    // Execute simulation method every 60secs
    @Scheduled(fixedRate = 60000)
    public void publishMessage() {
        Optional<BaseStationConfig> configOpt = configRepository.findById(1L);

        // Check if simulator is enabled
        if (configOpt.isEmpty() || !configOpt.get().isEnabled()) {
            logger.info("Publishing disabled in DB. Skipping message publish."); // log out to say that publish is disabled
            return;
        }

        // Create a message with simulated data **Update to send any Base Station values
        RadioBaseStationMessage message = new RadioBaseStationMessage();
        message.setNodeId(101);
        message.setNetworkId(202);
        message.setNetworkName("Network-Alpha");
        message.setCallsHandled(random.nextInt(50) + 1); // random calls between 1 and 50
        message.setMobileCallPeriod(LocalDateTime.now());

        kafkaTemplate.send(TOPIC, message);
        logger.info("Published message to topic {}: {}", TOPIC, message); // log out to notify user that message is published
    }
}
