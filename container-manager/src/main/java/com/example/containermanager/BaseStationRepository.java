package com.example.containermanager;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseStationRepository extends JpaRepository<BaseStation, Long> {
    List<BaseStation> findByNetworkId(String networkId);
    // You can define custom queries here if needed
}
