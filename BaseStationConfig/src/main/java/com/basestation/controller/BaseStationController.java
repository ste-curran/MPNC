package com.basestation.controller;

import com.basestation.dto.BaseStation;
import com.basestation.service.BaseStationService;
import com.basestation.service.BaseStationServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-stations")  // ✅ Matches frontend
@CrossOrigin(origins = "*")            // ✅ Allow frontend access
public class BaseStationController {

    private final BaseStationService baseStationService;

    public BaseStationController(BaseStationServiceImplementation baseStationService) {
        this.baseStationService = baseStationService;
    }

    @GetMapping
    public List<BaseStation> getAllBaseStations() {
        return baseStationService.getBaseStations();
    }

    @GetMapping("/{nodeId}")
    public ResponseEntity<BaseStation> getStation(@PathVariable int nodeId) {
        return baseStationService.getBaseStation(nodeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BaseStation> createOrUpdateBaseStation(@RequestBody BaseStation baseStation) {
        return ResponseEntity.ok(baseStationService.createBaseStation(baseStation));
    }

    @PutMapping("/{nodeId}")
    public ResponseEntity<Void> updateStreamingStatus(@PathVariable int nodeId, @RequestBody BaseStation input) {
        baseStationService.setStreamingEnabled(nodeId, input.isEnabled());
        return ResponseEntity.ok().build();
    }
}

