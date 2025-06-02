package com.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.dto.DataTable;
import org.springframework.stereotype.Service;
import com.project.repo.DataTableRepo;
import org.springframework.kafka.annotation.KafkaListener;


@Service
public class KafkaConsumeService implements KafkaConsumerServiceInterface {
    private DataTableRepo dataTableRepo;
    private final ObjectMapper objectMapper;
    public KafkaConsumeService(DataTableRepo dataTableRepo) {
        this.dataTableRepo = dataTableRepo;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    @KafkaListener(topics="mobile-data-topic", groupId ="consumer")
    @Override
    public void consumeNetworkData(String data){
        try{
            DataTable dataTable = objectMapper.readValue(data, DataTable.class);
            dataTableRepo.save(dataTable);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
