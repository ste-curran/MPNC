package com.tus.radiobasestation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// JPA Entity to be persisted to database. Perform CRUD operations to update config.
@Entity
@Table(name = "BASE_STATION_CONFIG") // map to BASE_STATION_CONFIG table
public class BaseStationConfig {

    @Id
    private Long id;

    private boolean enabled; // Allows config. for enable / disable (see 'boolean isEnabled()')

    // Instantiate entity
    public BaseStationConfig() {
    }

    // Can be used to initialise with config.
    public BaseStationConfig(Long id, boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
