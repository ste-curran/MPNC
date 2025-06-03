package com.basestation.controller;

import com.basestation.dto.BaseStation;
import com.basestation.service.BaseStationService;
import com.basestation.service.BaseStationServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BaseStationController.class)
public class BaseStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseStationServiceImplementation baseStationService;

    private BaseStation sampleStation;

    @BeforeEach
    public void setUp() {
        sampleStation = new BaseStation();
        sampleStation.setNodeId(101);
        sampleStation.setNetworkId(1);
        sampleStation.setNetworkName("Vodafone");
        sampleStation.setEnabled(true);
    }

    @Test
    public void testGetAllBaseStations() throws Exception {
        when(baseStationService.getBaseStations()).thenReturn(Arrays.asList(sampleStation));

        mockMvc.perform(get("/basestation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nodeId").value(101));
    }

    @Test
    public void testGetBaseStationById() throws Exception {
        when(baseStationService.getBaseStation(101)).thenReturn(Optional.of(sampleStation));

        mockMvc.perform(get("/basestation/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.networkName").value("Vodafone"));
    }

    @Test
    public void testIsEnabled() throws Exception {
        when(baseStationService.isStreamingEnabled(101)).thenReturn(true);

        mockMvc.perform(get("/basestation/101/enabled"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testCreateBaseStation() throws Exception {
        when(baseStationService.createBaseStation(any(BaseStation.class))).thenReturn(sampleStation);

        String json = """
        {
          "nodeId": 101,
          "networkId": 1,
          "networkName": "Vodafone",
          "streamingEnabled": true
        }
        """;

        mockMvc.perform(post("/basestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nodeId").value(101));
    }

    @Test
    public void testEnableBaseStation() throws Exception {
        mockMvc.perform(put("/basestation/101/enabled"))
                .andExpect(status().isOk());

        Mockito.verify(baseStationService).setStreamingEnabled(101, true);
    }

    @Test
    public void testDisableBaseStation() throws Exception {
        mockMvc.perform(put("/basestation/101/disabled"))
                .andExpect(status().isOk());

        Mockito.verify(baseStationService).setStreamingEnabled(101, false);
    }
}
