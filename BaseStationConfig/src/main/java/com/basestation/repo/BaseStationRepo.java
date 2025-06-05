package com.basestation.repo;

import com.basestation.dto.BaseStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BaseStationRepo extends JpaRepository<BaseStation, Integer> {
    List<BaseStation> findByEnabledTrue();
    @Query("SELECT COUNT(b) FROM BaseStation b WHERE b.enabled = true")
    long countEnabledStationsUsingQuery();
}
