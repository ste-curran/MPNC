package com.example.radiobasestation.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

// Use for Kafka messaging or REST serialization
public class RadioBaseStationMessage {

    private int nodeId;
    private int networkId;
    private String networkName;
    private int callsHandled;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime mobileCallPeriod;

    public RadioBaseStationMessage() {
    }

    // Send message payload to Kafka topic using required fields. Will be serialised as JSON
    public RadioBaseStationMessage(int nodeId, int networkId, String networkName, int callsHandled, LocalDateTime mobileCallPeriod) {
        this.nodeId = nodeId;
        this.networkId = networkId;
        this.networkName = networkName;
        this.callsHandled = callsHandled;
        this.mobileCallPeriod = mobileCallPeriod;
    }

    // Getters and setters

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public int getCallsHandled() {
        return callsHandled;
    }

    public void setCallsHandled(int callsHandled) {
        this.callsHandled = callsHandled;
    }

    public LocalDateTime getMobileCallPeriod() {
        return mobileCallPeriod;
    }

    public void setMobileCallPeriod(LocalDateTime mobileCallPeriod) {
        this.mobileCallPeriod = mobileCallPeriod;
    }
}
