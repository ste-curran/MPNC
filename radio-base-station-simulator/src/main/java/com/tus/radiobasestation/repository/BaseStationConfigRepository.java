package com.tus.radiobasestation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.radiobasestation.model.BaseStationConfig;

// Perform database operations on BaseConfig Entity
@Repository
public interface BaseStationConfigRepository extends JpaRepository<BaseStationConfig, Long> {
}
