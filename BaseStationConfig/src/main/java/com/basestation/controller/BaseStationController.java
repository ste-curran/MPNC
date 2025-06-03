package com.basestation.controller;

import com.basestation.dto.BaseStation;
import com.basestation.service.BaseStationService;
import com.basestation.service.BaseStationServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basestation")
public class BaseStationController {
    private BaseStationService baseStationService;
    public BaseStationController(BaseStationServiceImplementation baseStationService) {
        this.baseStationService = baseStationService;
    }
    @GetMapping
    public List<BaseStation> getAllBaseStations() {
        return baseStationService.getBaseStations();
    }
    @GetMapping("/{nodeId}")
    public ResponseEntity<BaseStation> enableStation(@PathVariable int nodeId){
        return baseStationService.getBaseStation(nodeId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{nodeId}/enabled")
    public boolean isEnabled(@PathVariable int nodeId){
        return baseStationService.isStreamingEnabled(nodeId);
    }
    @PutMapping("/{nodeId}/enabled")
    public ResponseEntity<Void> enableBaseStation(@PathVariable int nodeId){
        baseStationService.setStreamingEnabled(nodeId, true);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{nodeId}/disabled")
    public ResponseEntity<Void> disableBaseStation(@PathVariable int nodeId){
        baseStationService.setStreamingEnabled(nodeId, false);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/enabled")
    public List<BaseStation> getEnabledBaseStations() {
        return baseStationService.getBaseStations();
    }
    @PostMapping
    public ResponseEntity<BaseStation> createOrUpdateBaseStation(@RequestBody BaseStation baseStation){
        return ResponseEntity.ok(baseStationService.createBaseStation(baseStation));
    }

}
