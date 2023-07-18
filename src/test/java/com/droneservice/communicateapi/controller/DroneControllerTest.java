package com.droneservice.communicateapi.controller;

import com.droneservice.communicateapi.CommunicateApiConfig;
import com.droneservice.communicateapi.data.request.*;
import com.droneservice.communicateapi.data.response.*;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.entity.Medication;
import com.droneservice.communicateapi.entity.MedicationLoadDetails;
import com.droneservice.communicateapi.repository.DroneRepository;
import com.droneservice.communicateapi.repository.MedicationDeliveryDetailsRepository;
import com.droneservice.communicateapi.repository.MedicationLoadDetailsRepository;
import com.droneservice.communicateapi.repository.MedicationRepository;
import com.droneservice.communicateapi.util.DroneState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommunicateApiConfig.class})
@DataJpaTest
@ActiveProfiles({"test"})
class DroneControllerTest {

    @Autowired
    private DroneController droneController;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private MedicationDeliveryDetailsRepository medicationDeliveryDetailsRepository;

    @Autowired
    private MedicationLoadDetailsRepository medicationLoadDetailsRepository;

    @BeforeEach
    public void init() {
        DroneRegisterRequest drone = new DroneRegisterRequest("QWER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        MedicationRegisterRequest medication = new MedicationRegisterRequest("WER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        droneController.registerDrone(drone);
        droneController.registerMedication(medication);
    }

    @Test
    void registerDrone() {
        DroneRegisterRequest droneRegisterRequest = new DroneRegisterRequest("QWER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        ResponseEntity<DroneRegisterResponse> droneRegisterResponse = droneController.registerDrone(droneRegisterRequest);
        Assertions.assertThat(droneRegisterResponse.getBody()).extracting(DroneRegisterResponse::getSerialNumber).isEqualTo("QWER123KF");
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void registerMedication() {
        MedicationRegisterRequest medicationRegisterRequest = new MedicationRegisterRequest("WER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        ResponseEntity<MedicationRegisterResponse> medicationRegisterResponseResponse = droneController.registerMedication(medicationRegisterRequest);
        Assertions.assertThat(medicationRegisterResponseResponse.getBody()).extracting(MedicationRegisterResponse::getCode).isEqualTo("WER3E");
        medicationRepository.deleteAll();
        Assertions.assertThat(medicationRepository.findAll()).isEmpty();
    }

    @Test
    void getAvailableDronesForLoading() {
        ResponseEntity<AvailableDroneResponse> availableDroneResponseResponse = droneController.getAvailableDronesForLoading();
        Assertions.assertThat(availableDroneResponseResponse.getBody()).extracting(a -> a.getDrones().size()).isEqualTo(1);
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void checkDroneBatteryLevel() {
        DroneBatteryLevelRequest droneBatteryLevelRequest = new DroneBatteryLevelRequest("QWER123KF");
        ResponseEntity<DroneBatteryLevelResponse> droneBatteryLevelResponseResponse = droneController.checkDroneBatteryLevel(droneBatteryLevelRequest);
        Assertions.assertThat(droneBatteryLevelResponseResponse.getBody()).extracting(DroneBatteryLevelResponse::getSerialNumber).isEqualTo("QWER123KF");
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void loadDroneWithMedication() {
        DroneMedicationLoadRequest droneMedicationLoadRequest = new DroneMedicationLoadRequest("startPoint", "EndPoint",
                "QWER123KF", "WER3E");
        ResponseEntity<DroneMedicationLoadResponse> droneMedicationLoadResponseResponse = droneController.loadDroneWithMedication(droneMedicationLoadRequest);
        Assertions.assertThat(droneMedicationLoadResponseResponse.getBody()).extracting(DroneMedicationLoadResponse::getSerialNumber).isEqualTo("QWER123KF");
        medicationLoadDetailsRepository.deleteAll();
        Assertions.assertThat(medicationLoadDetailsRepository.findAll()).isEmpty();
    }

    @Test
    void droneMedicalLoadDelivery() {
        DroneMedicalDeliveryRequest droneMedicalDeliveryRequest = new DroneMedicalDeliveryRequest("QWER123KF");
        ResponseEntity<DroneMedicalDeliveryResponse> droneMedicalDeliveryResponseResponse = droneController.droneMedicalLoadDelivery(droneMedicalDeliveryRequest);
        Assertions.assertThat(droneMedicalDeliveryResponseResponse.getBody()).extracting(DroneMedicalDeliveryResponse::getSerialNumber).isEqualTo("QWER123KF");
        medicationDeliveryDetailsRepository.deleteAll();
        Assertions.assertThat(medicationDeliveryDetailsRepository.findAll()).isEmpty();
    }

    @Test
    void droneDetails() {
        DroneDetailsRequest droneDetailsRequest = new DroneDetailsRequest("QWER123KF");
        ResponseEntity<DroneDetailsResponse> droneDetailsResponse = droneController.droneDetails(droneDetailsRequest);
        Assertions.assertThat(droneDetailsResponse.getBody()).extracting(DroneDetailsResponse::getSerialNumber).isEqualTo("QWER123KF");
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }
}