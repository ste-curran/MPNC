package com.basestation.service;

import com.basestation.dto.BaseStation;
import com.basestation.repo.BaseStationRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BaseStationServiceImplementation implements BaseStationService {
    private BaseStationRepo baseStationRepo;
    public BaseStationServiceImplementation(BaseStationRepo baseStationRepo) {
        this.baseStationRepo = baseStationRepo;
    }
    @Override
    public List<BaseStation> getBaseStations() {
        return baseStationRepo.findAll();
    }
    @Override
    public Optional<BaseStation> getBaseStation(int nodeId) {
        return baseStationRepo.findById(nodeId);
    }
    @Override
    public boolean isStreamingEnabled(int nodeId) {
        return baseStationRepo.findById(nodeId).map(BaseStation::isEnabled).orElse(false);
    }
    @Override
    public BaseStation createBaseStation(BaseStation baseStation) {
        return baseStationRepo.save(baseStation);
    }
    @Override
    public void setStreamingEnabled(int nodeId, boolean enabled) {
        BaseStation baseStation = baseStationRepo.findById(nodeId).orElseThrow(() -> new RuntimeException("Base Station not found"));
        baseStation.setEnabled(enabled);
        baseStationRepo.save(baseStation);
    }
    @Override
    public List<BaseStation> getEnabledBaseStations(int nodeId) {
        return baseStationRepo.findByIsEnabledTrue();
    }
}
