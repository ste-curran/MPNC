package com.project.dto;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DataTable")
public class DataTable {
    @Id

    private int nodeId;

    private int networkId;

    private String networkName;
    private double callsHandled;
    private LocalDateTime mobileCallPeriod;
    public double getCallsHandled() {
        return callsHandled;
    }

    public void setNoOfCallsPerSixtySecond(double callsHandled) {
        this.callsHandled = callsHandled;
    }

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

    public LocalDateTime getMobilleCallPeriod() {
        return mobileCallPeriod;
    }

    public void setDateTime(LocalDateTime mobileCallPeriod) {
        this.mobileCallPeriod = mobileCallPeriod;
    }
}


