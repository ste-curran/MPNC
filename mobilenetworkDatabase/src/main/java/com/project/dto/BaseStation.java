package com.project.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Base_Station")
public class BaseStation {
    @Id
    private int nodeId;
    private int networkId;
    private String networkName;
    private boolean isEnabled;

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
