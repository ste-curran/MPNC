package com.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.dto.DataTable;
import com.project.repo.DataTableRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KafkaConsumeServiceTest {

    private DataTableRepo dataTableRepo;
    private KafkaConsumeService kafkaConsumeService;

    @BeforeEach
    void setUp() {
        dataTableRepo = mock(DataTableRepo.class);
        kafkaConsumeService = new KafkaConsumeService(dataTableRepo);
    }

    @Test
    void consumeNetworkData_validJson_savesToRepo() throws Exception {

        DataTable sample = new DataTable();
        sample.setNodeId(1);
        sample.setNetworkId(100);
        sample.setNetworkName("Test Network");
        sample.setNoOfCallsPerSixtySecond(23.5);
        sample.setDateTime(LocalDateTime.now());

        // Serialize to JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(sample);


        kafkaConsumeService.consumeNetworkData(json);


        ArgumentCaptor<DataTable> captor = ArgumentCaptor.forClass(DataTable.class);
        verify(dataTableRepo).save(captor.capture());

        DataTable saved = captor.getValue();


        assertEquals(sample.getNodeId(), saved.getNodeId());
        assertEquals(sample.getNetworkId(), saved.getNetworkId());
        assertEquals(sample.getNetworkName(), saved.getNetworkName());
        assertEquals(sample.getCallsHandled(), saved.getCallsHandled());
        assertEquals(sample.getMobileCallPeriod(), saved.getMobileCallPeriod());
    }

    @Test
    void consumeNetworkData_invalidJson_doesNotThrow() {
        String badJson = "not-json";
        assertDoesNotThrow(() -> kafkaConsumeService.consumeNetworkData(badJson));
        verify(dataTableRepo, never()).save(any());
    }
}

