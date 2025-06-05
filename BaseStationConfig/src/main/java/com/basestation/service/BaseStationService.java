package com.basestation.service;

import com.basestation.dto.BaseStation;

import java.util.List;
import java.util.Optional;

public interface BaseStationService {
    List<BaseStation> getBaseStations();
    Optional<BaseStation> getBaseStation(int nodeId);
    boolean isStreamingEnabled(int nodeId);
    BaseStation createBaseStation(BaseStation baseStation);
    void setStreamingEnabled(int nodeId, boolean enabled);
    List<BaseStation> getEnabledBaseStations(int nodeId);
    long countEnabledStations();
}
