package com.example.containermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
@CrossOrigin(origins = "*")
@RestController
public class BaseStationController {

    @Autowired
    private BaseStationRepository baseStationRepository; // Inject repository

    // Set to keep track of used ports
    private Set<Integer> usedPorts = new HashSet<>();
    private static final int BASE_PORT = 9000;  // Start at port 9000

    @PostMapping("/create-base-station")
    public String createBaseStation(@RequestBody BaseStationRequest request) {
        try {
            // Extract parameters from the request body
            String nodeId = request.getNodeId();
            String networkId = request.getNetworkId();
            String networkName = request.getNetworkName();
            String streamingEnabled = Boolean.toString(request.isStreamingEnabled());

            // Placeholder values for other environment variables
            String kafkaBroker = "kafka.broker.url";
            String mysqlHost = "mysql.host";
            String mysqlPort = "3306";
            String mysqlDb = "mydb";
            String mysqlUser = "root";
            String mysqlPassword = "root";
            String baseStationImage = "prod";

            // Find an available port
            int port = findAvailablePort();

            // Save the base station details in the database
            BaseStation baseStation = new BaseStation();
            baseStation.setNodeId(nodeId);
            baseStation.setNetworkId(networkId);
            baseStation.setNetworkName(networkName);
            baseStation.setStreamingEnabled(streamingEnabled);
            baseStation.setPort(port);

            baseStationRepository.save(baseStation);  // Save the base station in DB

            // Prepare the Docker command with the dynamic port
            String command = String.format(
                    "docker run -d --name base-station-%s --network tap_network -p %d:%d " +
                            "-e KAFKA_BROKER=%s " +
                            "-e SPRING_DATASOURCE_URL=jdbc:mysql://%s:%s/%s?useSSL=false " +
                            "-e SPRING_DATASOURCE_USERNAME=%s " +
                            "-e SPRING_DATASOURCE_PASSWORD=%s " +
                            "-e NODE_ID=%s " +
                            "-e NETWORK_ID=%s " +
                            "-e NETWORK_NAME=%s " +
                            "-e STREAMING_ENABLED=%s " +
                            "%s",
                    nodeId, port, 8080, kafkaBroker, mysqlHost, mysqlPort, mysqlDb, mysqlUser, mysqlPassword,
                    nodeId, networkId, networkName, streamingEnabled, baseStationImage
            );

            // Ensure Docker is in the PATH, but handling spaces in the path for Windows
            String dockerPath = "C:\\Program Files\\Docker\\Docker\\Resources\\bin\\docker.exe";

            // Construct full command for Windows, accounting for spaces
            String fullCommand = String.format(
                    "\"%s\" run -d --name base-station-%s --network tap_network -p %d:%d " +
                            "-e KAFKA_BROKER=%s " +
                            "-e SPRING_DATASOURCE_URL=jdbc:mysql://%s:%s/%s?useSSL=false " +
                            "-e SPRING_DATASOURCE_USERNAME=%s " +
                            "-e SPRING_DATASOURCE_PASSWORD=%s " +
                            "-e NODE_ID=%s " +
                            "-e NETWORK_ID=%s " +
                            "-e NETWORK_NAME=%s " +
                            "-e STREAMING_ENABLED=%s " +
                            "%s",
                    dockerPath, nodeId, port, 8080, kafkaBroker, mysqlHost, mysqlPort, mysqlDb, mysqlUser, mysqlPassword,
                    nodeId, networkId, networkName, streamingEnabled, baseStationImage
            );

            // Execute command via Runtime
            Process process = Runtime.getRuntime().exec(fullCommand);

            // Wait for the process to complete and check the exit code
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Base Station " + nodeId + " created and started successfully on port " + port + " and saved to DB!";
            } else {
                return "Error: Docker container failed to start.";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error creating Base Station: " + e.getMessage();
        }
    }

    // Method to find an available port
    private int findAvailablePort() {
        int port = BASE_PORT;
        // Loop to find an available port
        while (usedPorts.contains(port) || isPortInUse(port)) {
            port++; // Increment port
        }
        usedPorts.add(port); // Mark this port as used
        return port;
    }

    // Check if the port is already in use on the system (not just by other containers)
    private boolean isPortInUse(int port) {
        try {
            // Run the netstat command to check if the port is in use
            String command = String.format("netstat -ano | findstr :%d", port);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            // If the exit value is 0, that means the port is in use
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // If an error occurs, assume the port is free
        }
    }
}
