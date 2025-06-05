package com.basestation.service;

import com.basestation.dto.BaseStation;
import com.basestation.repo.BaseStationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BaseStationServiceTest {

    private BaseStationRepo baseStationRepo;
    private BaseStationServiceImplementation service;

    private BaseStation sample;

    @BeforeEach
    void setUp() {
        baseStationRepo = mock(BaseStationRepo.class);
        service = new BaseStationServiceImplementation(baseStationRepo);

        sample = new BaseStation();
        sample.setNodeId(101);
        sample.setNetworkId(1);
        sample.setNetworkName("TestNetwork");
        sample.setEnabled(true);
    }

    @Test
    void testGetAllStations() {
        when(baseStationRepo.findAll()).thenReturn(List.of(sample));

        List<BaseStation> result = service.getBaseStations();

        assertEquals(1, result.size());
        verify(baseStationRepo, times(1)).findAll();
    }

    @Test
    void testGetBaseStation() {
        when(baseStationRepo.findById(101)).thenReturn(Optional.of(sample));

        Optional<BaseStation> result = service.getBaseStation(101);

        assertTrue(result.isPresent());
        assertEquals(101, result.get().getNodeId());
    }

    @Test
    void testIsStreamingEnabled_true() {
        when(baseStationRepo.findById(101)).thenReturn(Optional.of(sample));

        boolean result = service.isStreamingEnabled(101);

        assertTrue(result);
    }

    @Test
    void testIsStreamingEnabled_falseWhenNotFound() {
        when(baseStationRepo.findById(999)).thenReturn(Optional.empty());

        boolean result = service.isStreamingEnabled(999);

        assertFalse(result);
    }

    @Test
    void testCreateBaseStation() {
        when(baseStationRepo.save(any(BaseStation.class))).thenReturn(sample);

        BaseStation result = service.createBaseStation(sample);

        assertEquals("TestNetwork", result.getNetworkName());
        verify(baseStationRepo, times(1)).save(sample);
    }

    @Test
    void testSetStreamingEnabled_true() {
        when(baseStationRepo.findById(101)).thenReturn(Optional.of(sample));

        service.setStreamingEnabled(101, false);

        ArgumentCaptor<BaseStation> captor = ArgumentCaptor.forClass(BaseStation.class);
        verify(baseStationRepo).save(captor.capture());

        assertFalse(captor.getValue().isEnabled());
    }

    @Test
    void testGetEnabledStations() {
        when(baseStationRepo.findByEnabledTrue()).thenReturn(Arrays.asList(sample));

        List<BaseStation> result = service.getEnabledBaseStations(0); // nodeId isn't used in method

        assertEquals(1, result.size());
        assertTrue(result.get(0).isEnabled());
    }
}
