package com.tus.radiobasestationconsumer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RadioBaseStationMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer nodeId;
	private Integer networkId;
	private String networkName;
	private Integer callsHandled;
	private LocalDateTime mobileCallPeriod;

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

	public Integer getCallsHandled() {
		return callsHandled;
	}

	public void setCallsHandled(Integer callsHandled) {
		this.callsHandled = callsHandled;
	}

	public LocalDateTime getMobileCallPeriod() {
		return mobileCallPeriod;
	}

	public void setMobileCallPeriod(LocalDateTime mobileCallPeriod) {
		this.mobileCallPeriod = mobileCallPeriod;
	}

}
