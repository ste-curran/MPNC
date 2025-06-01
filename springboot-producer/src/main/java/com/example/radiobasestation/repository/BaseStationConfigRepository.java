package com.example.radiobasestation.repository;

import com.example.radiobasestation.model.BaseStationConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Perform database operations on BaseConfig Entity
@Repository
public interface BaseStationConfigRepository extends JpaRepository<BaseStationConfig, Long> {
}
