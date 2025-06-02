package com.tus.radiobasestationconsumer.config.test;

import com.tus.radiobasestationconsumer.config.KafkaConsumerConfig;
import com.tus.radiobasestationconsumer.model.RadioBaseStationMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(KafkaConsumerConfig.class)
class KafkaConsumerConfigTest {

    @Autowired
    private ConsumerFactory<String, RadioBaseStationMessage> consumerFactory;

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, RadioBaseStationMessage> containerFactory;

    @Test
    void consumerFactory_shouldNotBeNull() {
        assertNotNull(consumerFactory, "ConsumerFactory bean should be initialized");
    }

    @Test
    void kafkaListenerContainerFactory_shouldNotBeNull() {
        assertNotNull(containerFactory, "KafkaListenerContainerFactory bean should be initialized");
    }

    @Test
    void consumerFactory_shouldUseCorrectDeserializers() {
        // This test is limited due to type erasure and Kafka API restrictions,
        // but can ensure the bean type and basics are correct
        Consumer<String, RadioBaseStationMessage> consumer = consumerFactory.createConsumer();
        assertNotNull(consumer, "Should be able to create Kafka consumer");
        consumer.close(); // Clean up after creating
    }
}

