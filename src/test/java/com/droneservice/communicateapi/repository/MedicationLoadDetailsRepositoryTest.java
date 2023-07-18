package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.CommunicateApiConfig;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.entity.Medication;
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
class MedicationLoadDetailsRepositoryTest {

    @Autowired
    private MedicationLoadDetailsRepository medicationLoadDetailsRepository;

    private MedicationLoadDetails medicationLoadDetails;

    @BeforeEach
    public void init() {
        Drone drone = new Drone("QWER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        Medication medication = new Medication("WER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        medicationLoadDetails = new MedicationLoadDetails(1, "startPoint", "EndPoint",
                LocalDateTime.now(), drone, medication);
        medicationLoadDetailsRepository.save(medicationLoadDetails);
    }

    @Test
    void findByCode() {
        MedicationLoadDetails detailsRepositoryByCode = medicationLoadDetailsRepository.findByCode("WER3E");
        Assertions.assertThat(detailsRepositoryByCode).extracting(m -> m.getMedication().getCode())
                .isEqualTo(medicationLoadDetails.getMedication().getCode());
        medicationLoadDetailsRepository.deleteAll();
        Assertions.assertThat(medicationLoadDetailsRepository.findAll()).isEmpty();
    }

    @Test
    void findByDrone() {
        MedicationLoadDetails detailsRepositoryByDrone = medicationLoadDetailsRepository.findByDrone("QWER123KF");
        Assertions.assertThat(detailsRepositoryByDrone).extracting(m -> m.getDrone().getSerialNumber())
                .isEqualTo(medicationLoadDetails.getDrone().getSerialNumber());
        medicationLoadDetailsRepository.deleteAll();
        Assertions.assertThat(medicationLoadDetailsRepository.findAll()).isEmpty();
    }
}