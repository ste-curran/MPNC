package com.tus.radiobasestationconsumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.*;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.tus.radiobasestationconsumer.model.RadioBaseStationMessage;

import java.util.*;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, RadioBaseStationMessage> consumerFactory() {
    	// set up conversion of JSON objects into RadioBaseStationMessage objects
        JsonDeserializer<RadioBaseStationMessage> deserializer = new JsonDeserializer<>(RadioBaseStationMessage.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        // set up Kafka for processing messages from topic using deserialisation
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "radio-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    // Handle message processing. Tell Spring how to create listener containers for @KafkaListener methods
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RadioBaseStationMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RadioBaseStationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
