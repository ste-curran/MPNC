package com.example.containermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")  // Allow only requests from your frontend
@RestController
@RequestMapping("/api")  // Adding the /api prefix to all routes
public class BaseStationController {

    @Autowired
    private BaseStationRepository baseStationRepository;

    // Set to keep track of used ports
    private Set<Integer> usedPorts = new HashSet<>();
    private static final int BASE_PORT = 9000;  // Start at port 9000

    // Create a new base station (POST /api/base-stations)
    @PostMapping("/base-stations")
    public ResponseEntity<String> createBaseStation(@RequestBody BaseStationRequest request) {
        String nodeId = request.getNodeId();
        String networkId = request.getNetworkId();
        String networkName = request.getNetworkName();
        String streamingEnabled = Boolean.toString(request.isStreamingEnabled());

        // Placeholder for Docker setup variables (you should replace these with actual logic)
        String kafkaBroker = "kafka.broker.url";
        String mysqlHost = "mysql.host";
        String mysqlPort = "3306";
        String mysqlDb = "mydb";
        String mysqlUser = "root";
        String mysqlPassword = "root";
        String baseStationImage = "prod";

        // Find an available port
        int port = findAvailablePort();

        // Save the base station in DB
        BaseStation baseStation = new BaseStation();
        baseStation.setNodeId(nodeId);
        baseStation.setNetworkId(networkId);
        baseStation.setNetworkName(networkName);
        baseStation.setStreamingEnabled(streamingEnabled);
        baseStation.setPort(port);

        baseStationRepository.save(baseStation);

        // Return response
        return ResponseEntity.status(HttpStatus.CREATED).body("Base Station " + nodeId + " created successfully on port " + port);
    }

    // Method to find an available port
    private int findAvailablePort() {
        int port = BASE_PORT;
        while (usedPorts.contains(port) || isPortInUse(port)) {
            port++;  // Increment port
        }
        usedPorts.add(port);  // Mark this port as used
        return port;
    }

    // Check if the port is in use
    private boolean isPortInUse(int port) {
        try {
            String command = String.format("netstat -ano | findstr :%d", port);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;  // If an error occurs, assume the port is free
        }
    }

    // Get all base stations (GET /api/base-stations)
    @GetMapping("/base-stations")
    public ResponseEntity<List<BaseStation>> getAllBaseStations() {
        try {
            List<BaseStation> stations = baseStationRepository.findAll();
            if (stations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(stations);
            }
            return ResponseEntity.ok(stations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get all base stations by networkId (optional endpoint)
    @GetMapping("/base-stations/{networkId}")
    public ResponseEntity<List<BaseStation>> getBaseStationsByNetworkId(@PathVariable String networkId) {
        try {
            List<BaseStation> stations = baseStationRepository.findByNetworkId(networkId);
            if (stations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(stations);
            }
            return ResponseEntity.ok(stations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to toggle the base station's enabled status (PUT /api/base-stations/{nodeId})
    @PutMapping("/base-stations/{nodeId}")
    public ResponseEntity<String> toggleBaseStation(@PathVariable String nodeId, @RequestBody ToggleRequest request) {
        try {
            BaseStation baseStation = baseStationRepository.findById(Long.parseLong(nodeId)).orElse(null);
            if (baseStation == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Base station with node ID " + nodeId + " not found.");
            }

            // Toggle the streamingEnabled status
            baseStation.setStreamingEnabled(Boolean.toString(request.isEnabled()));
            baseStationRepository.save(baseStation);

            return ResponseEntity.ok("Base station status updated.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error toggling base station: " + e.getMessage());
        }
    }

    // Inner class to capture the toggle request
    public static class ToggleRequest {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
