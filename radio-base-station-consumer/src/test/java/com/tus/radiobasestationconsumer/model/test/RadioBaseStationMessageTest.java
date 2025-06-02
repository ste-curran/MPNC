package com.tus.radiobasestationconsumer.model.test;

import org.junit.jupiter.api.Test;

import com.tus.radiobasestationconsumer.model.RadioBaseStationMessage;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RadioBaseStationMessageTest {

    @Test
    void testGettersAndSetters() {
        RadioBaseStationMessage message = new RadioBaseStationMessage();

        Long id = 1L;
        Integer nodeId = 101;
        Integer networkId = 202;
        String networkName = "Network-Alpha";
        Integer callsHandled = 25;
        LocalDateTime callPeriod = LocalDateTime.now();

        message.setId(id);
        message.setNodeId(nodeId);
        message.setNetworkId(networkId);
        message.setNetworkName(networkName);
        message.setCallsHandled(callsHandled);
        message.setMobileCallPeriod(callPeriod);

        assertEquals(id, message.getId());
        assertEquals(nodeId, message.getNodeId());
        assertEquals(networkId, message.getNetworkId());
        assertEquals(networkName, message.getNetworkName());
        assertEquals(callsHandled, message.getCallsHandled());
        assertEquals(callPeriod, message.getMobileCallPeriod());
    }

    @Test
    void testDefaultConstructor() {
        RadioBaseStationMessage message = new RadioBaseStationMessage();
        assertNotNull(message);
    }
}

