package com.basestation.controller;

import com.basestation.dto.BaseStation;
import com.basestation.service.BaseStationService;
import com.basestation.service.BaseStationServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BaseStationController.class)
public class BaseStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseStationServiceImplementation baseStationService;

    @Test
    void testGetAllBaseStations() throws Exception {
        BaseStation b1 = new BaseStation();
        BaseStation b2 = new BaseStation();

        when(baseStationService.getBaseStations()).thenReturn(Arrays.asList(b1, b2));

        mockMvc.perform(get("/api/base-stations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetStationFound() throws Exception {
        BaseStation station = new BaseStation();
        station.setNodeId(1);
        station.setEnabled(true);

        when(baseStationService.getBaseStation(1)).thenReturn(Optional.of(station));

        mockMvc.perform(get("/api/base-stations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodeId").value(1));
    }

    @Test
    void testGetStationNotFound() throws Exception {
        when(baseStationService.getBaseStation(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/base-stations/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrUpdateBaseStation() throws Exception {
        BaseStation station = new BaseStation();
        station.setNodeId(1);
        station.setEnabled(true);
        station.setNetworkId(123);
        station.setNetworkName("Test");

        when(baseStationService.createBaseStation(any(BaseStation.class))).thenReturn(station);

        String json = """
            {
              "nodeId": 1,
              "networkId": 123,
              "networkName": "Test",
              "enabled": true
            }
        """;

        mockMvc.perform(post("/api/base-stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodeId").value(1))
                .andExpect(jsonPath("$.networkName").value("Test"));
    }

    @Test
    void testUpdateStreamingStatus() throws Exception {
        String json = """
            {
              "enabled": false
            }
        """;

        doNothing().when(baseStationService).setStreamingEnabled(eq(1), eq(false));

        mockMvc.perform(put("/api/base-stations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
