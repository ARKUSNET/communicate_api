package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.CommunicateApiConfig;
import com.droneservice.communicateapi.entity.Medication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommunicateApiConfig.class})
@DataJpaTest
@ActiveProfiles({"test"})
class MedicationRepositoryTest {

    @Autowired
    private MedicationRepository medicationRepository;

    @BeforeEach
    public void init() {
        Medication medication = new Medication("WER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        medicationRepository.save(medication);
    }

    @Test
    void findByCode() {
        Medication medication = medicationRepository.findByCode("WER3E");
        Assertions.assertThat(medication).extracting(Medication::getCode).isEqualTo(medication.getCode());
        medicationRepository.deleteAll();
        Assertions.assertThat(medicationRepository.findAll()).isEmpty();
    }
}