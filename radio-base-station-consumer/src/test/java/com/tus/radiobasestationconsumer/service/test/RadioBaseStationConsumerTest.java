package com.tus.radiobasestationconsumer.service.test;

import com.tus.radiobasestationconsumer.model.RadioBaseStationMessage;
import com.tus.radiobasestationconsumer.repository.RadioBaseStationMessageRepository;
import com.tus.radiobasestationconsumer.service.RadioBaseStationConsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

class RadioBaseStationConsumerTest {

    @Mock
    private RadioBaseStationMessageRepository repository;

    @InjectMocks
    private RadioBaseStationConsumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initializes mocks
    }

    @Test
    void testConsume_shouldSaveMessage() {
        // Arrange
        RadioBaseStationMessage message = new RadioBaseStationMessage();
        message.setNodeId(101);
        message.setNetworkId(202);
        message.setNetworkName("Network Alpha");
        message.setCallsHandled(42);
        message.setMobileCallPeriod(LocalDateTime.now());

        // Act
        consumer.consume(message);

        // Assert
        verify(repository, times(1)).save(message);
    }
}
