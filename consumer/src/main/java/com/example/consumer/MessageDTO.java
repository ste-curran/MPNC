package com.example.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {
    @JsonProperty("node_id")
    private Integer nodeId;

    @JsonProperty("network_id")
    private Integer networkId;

    @JsonProperty("network_name")
    private String networkName;

    @JsonProperty("num_calls")
    private Integer numCalls;

    // Getters and Setters
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

