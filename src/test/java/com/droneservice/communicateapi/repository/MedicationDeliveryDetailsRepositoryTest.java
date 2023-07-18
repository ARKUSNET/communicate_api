package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.CommunicateApiConfig;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.entity.Medication;
import com.droneservice.communicateapi.entity.MedicationDeliveryDetails;
import com.droneservice.communicateapi.entity.MedicationLoadDetails;
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
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommunicateApiConfig.class})
@DataJpaTest
@ActiveProfiles({"test"})
class MedicationDeliveryDetailsRepositoryTest {

    @Autowired
    private MedicationDeliveryDetailsRepository medicationDeliveryDetailsRepository;

    @BeforeEach
    public void init() {
        Drone drone = new Drone("QWER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        Medication medication = new Medication("WER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        MedicationLoadDetails medicationLoadDetails = new MedicationLoadDetails(1, "startPoint", "EndPoint",
                LocalDateTime.now(), drone, medication);
        MedicationDeliveryDetails medicationDeliveryDetails = new MedicationDeliveryDetails(1, LocalDateTime.now(), medicationLoadDetails);
        medicationDeliveryDetailsRepository.save(medicationDeliveryDetails);
    }

    @Test
    void findAllByState() {
        Assertions.assertThat(medicationDeliveryDetailsRepository.findAll()).isNotEmpty();
        medicationDeliveryDetailsRepository.deleteAll();
        Assertions.assertThat(medicationDeliveryDetailsRepository.findAll()).isEmpty();
    }
}