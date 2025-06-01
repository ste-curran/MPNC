package com.tus.radiobasestation.config.test;

import com.example.radiobasestation.config.KafkaProducerConfig;
import com.example.radiobasestation.model.RadioBaseStationMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerConfigTest {

    KafkaProducerConfig config = new KafkaProducerConfig();

    @Test
    void testProducerFactoryBean() {
        ProducerFactory<String, RadioBaseStationMessage> factory = config.producerFactory();

        assertNotNull(factory);
        assertTrue(factory instanceof DefaultKafkaProducerFactory);

        // Optional: test internal config
        Map<String, Object> configMap = ((DefaultKafkaProducerFactory<String, RadioBaseStationMessage>) factory).getConfigurationProperties();

        assertEquals("localhost:9092", configMap.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(StringSerializer.class, configMap.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        assertEquals(JsonSerializer.class, configMap.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }

    @Test
    void testKafkaTemplateBean() {
        KafkaTemplate<String, RadioBaseStationMessage> kafkaTemplate = config.kafkaTemplate();

        assertNotNull(kafkaTemplate);
        assertEquals(DefaultKafkaProducerFactory.class, kafkaTemplate.getProducerFactory().getClass());
    }
}
