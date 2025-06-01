package com.tus.radiobasestation.model.test;

import com.example.radiobasestation.model.RadioBaseStationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RadioBaseStationMessageTest {

	// Registers JavaTimeModule - handle date/time classes e.g. LocalDateTime
    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // use ISO-8601
        return mapper;
    }

    @Test
    void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.of(2025, 6, 1, 12, 30);
        RadioBaseStationMessage message = new RadioBaseStationMessage(
                1, 100, "TestNetwork", 42, now);

        assertEquals(1, message.getNodeId());
        assertEquals(100, message.getNetworkId());
        assertEquals("TestNetwork", message.getNetworkName());
        assertEquals(42, message.getCallsHandled());
        assertEquals(now, message.getMobileCallPeriod());
    }

    @Test
    void testSetters() {
        RadioBaseStationMessage message = new RadioBaseStationMessage();
        LocalDateTime time = LocalDateTime.of(2025, 6, 1, 14, 0);

        message.setNodeId(2);
        message.setNetworkId(200);
        message.setNetworkName("UpdatedNetwork");
        message.setCallsHandled(99);
        message.setMobileCallPeriod(time);

        assertEquals(2, message.getNodeId());
        assertEquals(200, message.getNetworkId());
        assertEquals("UpdatedNetwork", message.getNetworkName());
        assertEquals(99, message.getCallsHandled());
        assertEquals(time, message.getMobileCallPeriod());
    }

    @Test
    void testJsonSerialization() throws JsonProcessingException {
        ObjectMapper mapper = objectMapper();
        LocalDateTime time = LocalDateTime.of(2025, 6, 1, 15, 45);
        RadioBaseStationMessage message = new RadioBaseStationMessage(
                3, 300, "JSONNet", 12, time);

        String json = mapper.writeValueAsString(message);
        assertTrue(json.contains("\"nodeId\":3"));
        assertTrue(json.contains("\"networkName\":\"JSONNet\""));
        assertTrue(json.contains("\"mobileCallPeriod\":\"2025-06-01T15:45:00\""));
    }

    @Test
    void testJsonDeserialization() throws JsonProcessingException {
        ObjectMapper mapper = objectMapper();
        String json = """
                {
                    "nodeId": 4,
                    "networkId": 400,
                    "networkName": "DeserNet",
                    "callsHandled": 23,
                    "mobileCallPeriod": "2025-06-01T16:00:00"
                }
                """;

        RadioBaseStationMessage message = mapper.readValue(json, RadioBaseStationMessage.class);
        assertEquals(4, message.getNodeId());
        assertEquals(400, message.getNetworkId());
        assertEquals("DeserNet", message.getNetworkName());
        assertEquals(23, message.getCallsHandled());
        assertEquals(LocalDateTime.of(2025, 6, 1, 16, 0), message.getMobileCallPeriod());
    }
}
