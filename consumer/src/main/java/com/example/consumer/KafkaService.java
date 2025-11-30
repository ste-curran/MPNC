package com.example.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaService {
    @Autowired
    private MessageRepository messageRepository;

    // ObjectMapper to convert JSON to Java objects
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "test", groupId = "my-group")
    public void listen(String message) {
        try {
            // Deserialize the JSON string into a MessageDTO object
            MessageDTO messageDTO = objectMapper.readValue(message, MessageDTO.class);

            // Convert the MessageDTO to a MessageEntity
            MessageEntity entity = new MessageEntity(
                messageDTO.getNodeId(),
                messageDTO.getNetworkId(),
                messageDTO.getNetworkName(),
                messageDTO.getNumCalls()
            );

            // Save the entity to the database
            messageRepository.save(entity);
            System.out.println("Saved to MySQL: " + entity);

        } catch (Exception e) {
            System.out.println("Failed to process message: " + message);
            e.printStackTrace();
        }
    }
}

