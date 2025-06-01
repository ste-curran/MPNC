package com.tus.radiobasestationconsumer.repository;

import com.tus.radiobasestationconsumer.model.RadioBaseStationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadioBaseStationMessageRepository extends JpaRepository<RadioBaseStationMessage, Long> {
}
