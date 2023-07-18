package com.droneservice.communicateapi.audit;

import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DecimalFormat;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DronesBatteryLevelLog {

    static final Logger log = LoggerFactory.getLogger(DronesBatteryLevelLog.class);

    private final DroneRepository droneRepository;

    @Autowired
    public DronesBatteryLevelLog(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Scheduled(fixedDelayString = "${audit.delay.schedule:10000}")
    public void scheduleInfo() {
        List<Drone> drones = droneRepository.findAll();
        DecimalFormat decFormat = new DecimalFormat("#%");
        drones.forEach(dron -> log.info("Drone: SerialNumber - {}, Battery Level - {}", dron.getSerialNumber(), decFormat.format(dron.getBatteryCapacity())));
    }
}
