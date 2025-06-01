package com.tus.radiobasestation.service.test;

import com.example.radiobasestation.model.BaseStationConfig;
import com.example.radiobasestation.model.RadioBaseStationMessage;
import com.example.radiobasestation.repository.BaseStationConfigRepository;
import com.example.radiobasestation.service.RadioBaseStationSimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RadioBaseStationSimulatorTest {

    @Mock
    private BaseStationConfigRepository configRepository;

    @Mock
    private KafkaTemplate<String, RadioBaseStationMessage> kafkaTemplate;

    @InjectMocks
    private RadioBaseStationSimulator simulator;

    private static final String TOPIC = "radio-base-station-topic";

    @BeforeEach
    void setUp() {
        // Ensures Random behaves consistently if needed, or reset mocks
        Mockito.reset(configRepository, kafkaTemplate);
    }

    @Test
    void testPublishMessage_configMissing_shouldNotSend() {
        // Arrange
        when(configRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        simulator.publishMessage();

        // Assert
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    void testPublishMessage_configDisabled_shouldNotSend() {
        // Arrange
        BaseStationConfig config = new BaseStationConfig();
        config.setEnabled(false);
        when(configRepository.findById(1L)).thenReturn(Optional.of(config));

        // Act
        simulator.publishMessage();

        // Assert
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    void testPublishMessage_configEnabled_shouldSendMessage() {
        // Arrange
        BaseStationConfig config = new BaseStationConfig();
        config.setEnabled(true);
        when(configRepository.findById(1L)).thenReturn(Optional.of(config));

        // Act
        simulator.publishMessage();

        // Assert
        verify(kafkaTemplate, times(1)).send(eq(TOPIC), argThat(message -> {
            return message.getNodeId() == 101 &&
                   message.getNetworkId() == 202 &&
                   "Network-Alpha".equals(message.getNetworkName()) &&
                   message.getCallsHandled() >= 1 &&
                   message.getCallsHandled() <= 50 &&
                   message.getMobileCallPeriod() != null;
        }));
    }
}
