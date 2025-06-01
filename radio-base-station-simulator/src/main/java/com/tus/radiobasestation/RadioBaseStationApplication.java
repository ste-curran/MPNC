package com.tus.radiobasestation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RadioBaseStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadioBaseStationApplication.class, args);
    }
}
