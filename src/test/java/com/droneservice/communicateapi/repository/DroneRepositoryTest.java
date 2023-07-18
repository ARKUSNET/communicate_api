package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.CommunicateApiConfig;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.util.DroneState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommunicateApiConfig.class})
@DataJpaTest
@ActiveProfiles({"test"})
class DroneRepositoryTest {

    @Autowired
    private DroneRepository droneRepository;

    private Drone drone;

    @BeforeEach
    public void init() {
        drone = new Drone("QWER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        droneRepository.save(drone);
    }

    @Test
    void findAllByState() {
        Iterable<Drone> drones = droneRepository.findAll();
        Assertions.assertThat(drones).extracting(Drone::getSerialNumber).contains(drone.getSerialNumber());
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void findBySerialNumber() {
        Drone findDrone = droneRepository.findBySerialNumber("QWER123KF");
        Assertions.assertThat(findDrone).extracting(Drone::getSerialNumber).isEqualTo(drone.getSerialNumber());
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }
}