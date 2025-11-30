package com.example.consumer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nodeId;
    private Integer networkId;
    private String networkName;
    private Integer numCalls;

    // Default constructor for JPA
    public MessageEntity() {
    }

    // Constructor to initialize the fields
    public MessageEntity(Integer nodeId, Integer networkId, String networkName, Integer numCalls) {
        this.nodeId = nodeId;
        this.networkId = networkId;
        this.networkName = networkName;
        this.numCalls = numCalls;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public Integer getNumCalls() {
        return numCalls;
    }

    public void setNumCalls(Integer numCalls) {
        this.numCalls = numCalls;
    }
}

