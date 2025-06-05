package com.example.containermanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseStationRepository extends JpaRepository<BaseStation, Long> {
    // You can define custom queries here if needed
}
