package com.example.radiobasestation.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.radiobasestation.model.RadioBaseStationMessage;

import java.util.HashMap;
import java.util.Map;

 // Config: Register @Bean methods in application context
@Configuration
public class KafkaProducerConfig {

	// Produce Kafka messages
	@Bean
	public ProducerFactory<String, RadioBaseStationMessage> producerFactory() {
	    Map<String, Object> configProps = new HashMap<>();
	    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Kafka will serialize the message key as a string
	    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Kafka will serialize the message value as JSON. Java object -> JSON
	    return new DefaultKafkaProducerFactory<>(configProps); // used to create Kafka producer client: a component that is responsible for sending messages to a Kafka topic
	}

	// Creates a KafkaTemplate bean. Used to send messages to Kafka in Spring Boot.
	@Bean
	public KafkaTemplate<String, RadioBaseStationMessage> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	}

}
