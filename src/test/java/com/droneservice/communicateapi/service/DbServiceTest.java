package com.droneservice.communicateapi.service;

import com.droneservice.communicateapi.CommunicateApiConfig;
import com.droneservice.communicateapi.data.request.DroneMedicationLoadRequest;
import com.droneservice.communicateapi.data.request.DroneRegisterRequest;
import com.droneservice.communicateapi.data.request.MedicationRegisterRequest;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.entity.Medication;
import com.droneservice.communicateapi.entity.MedicationDeliveryDetails;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommunicateApiConfig.class})
@DataJpaTest
@ActiveProfiles({"test"})
class DbServiceTest {

    @Autowired
    private DbService dbService;

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
        Medication medication = new Medication("WER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        Drone drone = new Drone("QWER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        droneRepository.saveAndFlush(drone);
        medicationRepository.saveAndFlush(medication);
    }

    @Test
    void saveNewDrone() {
        DroneRegisterRequest droneRegisterRequest = new DroneRegisterRequest("Q1WER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        Drone drone = dbService.saveNewDrone(droneRegisterRequest);
        Assertions.assertThat(drone).extracting(Drone::getSerialNumber).isEqualTo("Q1WER123KF");
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void saveNewMedication() {
        MedicationRegisterRequest medicationRegisterRequest = new MedicationRegisterRequest("W2ER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        Medication medication = dbService.saveNewMedication(medicationRegisterRequest);
        Assertions.assertThat(medication).extracting(Medication::getCode).isEqualTo("W2ER3E");
        medicationRepository.deleteAll();
        Assertions.assertThat(medicationRepository.findAll()).isEmpty();
    }

    @Test
    void findAllAvailableDrones() {
        List<Drone> list = dbService.findAllAvailableDrones();
        assertEquals(1, list.size());
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void findDroneBySerialNumberBatteryLoad() {
        Drone drone = findDrone("QWER123KF");
        Assertions.assertThat(drone).extracting(Drone::getSerialNumber).isEqualTo("QWER123KF");
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void findDroneBySerialNumberLoad() {
        Drone drone = findDrone("QWER123KF");
        Assertions.assertThat(drone).extracting(Drone::getSerialNumber).isEqualTo("QWER123KF");
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void findDroneBySerialNumberDelivery() {
        Drone drone = findDrone("QWER123KF");
        Assertions.assertThat(drone).extracting(Drone::getSerialNumber).isEqualTo("QWER123KF");
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void findMedicationByCode() {
        Medication medication = dbService.findMedicationByCode("WER3E");
        Assertions.assertThat(medication).extracting(Medication::getCode).isEqualTo("WER3E");
        medicationRepository.deleteAll();
        Assertions.assertThat(medicationRepository.findAll()).isEmpty();
    }

    @Test
    void findMedicationLoadDetailsByCode() {
        Medication medication = new Medication("WER3E555", "Aspirin", 25.0, "Aspirin.jpeg");
        Drone drone = new Drone("QWER123KF55", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        MedicationLoadDetails medicationLoadDetails = new MedicationLoadDetails(1, "startPoint", "EndPoint",
                LocalDateTime.now(), drone, medication);
        medicationLoadDetailsRepository.saveAndFlush(medicationLoadDetails);
        MedicationLoadDetails medicationLoadDetailsByCode = dbService.findMedicationLoadDetailsByCode("WER3E555");
        Assertions.assertThat(medicationLoadDetailsByCode).extracting(a -> a.getMedication().getCode()).isEqualTo("WER3E555");
        medicationLoadDetailsRepository.deleteAll();
        Assertions.assertThat(medicationLoadDetailsRepository.findAll()).isEmpty();
    }

    @Test
    void findMedicationLoadDetailsBySerialNumber() {
        Medication medication = new Medication("WER3E55", "Aspirin", 25.0, "Aspirin.jpeg");
        Drone drone = new Drone("QWER123KF55", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        MedicationLoadDetails medicationLoadDetails = new MedicationLoadDetails(1, "startPoint", "EndPoint",
                LocalDateTime.now(), drone, medication);
        medicationLoadDetailsRepository.saveAndFlush(medicationLoadDetails);
        MedicationLoadDetails medicationLoadDetailsNew = dbService.findMedicationLoadDetailsBySerialNumber("QWER123KF55");
        Assertions.assertThat(medicationLoadDetailsNew).extracting(a -> a.getDrone().getSerialNumber()).isEqualTo("QWER123KF55");
        medicationLoadDetailsRepository.deleteAll();
        Assertions.assertThat(medicationLoadDetailsRepository.findAll()).isEmpty();
    }

    @Test
    void setProcessLoading() {
        Drone drone = new Drone("1123LSD23", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        dbService.setProcessLoading(drone);
        Assertions.assertThat(drone).extracting(Drone::getState).isEqualTo(DroneState.LOADING);
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void setProcessLoaded() {
        Drone drone = new Drone("1123LSD22", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        dbService.setProcessLoaded(drone);
        Assertions.assertThat(drone).extracting(Drone::getState).isEqualTo(DroneState.LOADED);
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void setProcessDelivering() {
        Drone drone = new Drone("1123LSD42", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        dbService.setProcessDelivering(drone);
        Assertions.assertThat(drone).extracting(Drone::getState).isEqualTo(DroneState.DELIVERING);
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void setProcessDelivered() {
        Drone drone = new Drone("1123LSD22", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        dbService.setProcessDelivered(drone);
        Assertions.assertThat(drone).extracting(Drone::getState).isEqualTo(DroneState.DELIVERED);
        droneRepository.deleteAll();
        Assertions.assertThat(droneRepository.findAll()).isEmpty();
    }

    @Test
    void setMedicationLoadDetailsToDrone() {
        Medication medication = new Medication("WER3E444", "Aspirin", 25.0, "Aspirin.jpeg");
        Drone drone = new Drone("QWER123KF444", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        DroneMedicationLoadRequest droneMedicationLoadRequest = new DroneMedicationLoadRequest();
        droneMedicationLoadRequest.setStartPoint("startPoint");
        droneMedicationLoadRequest.setEndPoint("EndPoint");
        droneMedicationLoadRequest.setCode("WER3E");
        droneMedicationLoadRequest.setSerialNumber("QWER123KF");
        dbService.setMedicationLoadDetailsToDrone(drone, medication, droneMedicationLoadRequest);
        List<MedicationLoadDetails> list = medicationLoadDetailsRepository.findAll();
        assertEquals(1, list.size());
        medicationLoadDetailsRepository.deleteAll();
        Assertions.assertThat(medicationLoadDetailsRepository.findAll()).isEmpty();
    }

    @Test
    void setMedicalDeliveryDetails() {
        Medication medication = new Medication("WER333E", "Aspirin", 25.0, "Aspirin.jpeg");
        Drone drone = new Drone("QWER12333KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        MedicationLoadDetails medicationLoadDetails = new MedicationLoadDetails();
        medicationLoadDetails.setStartPoint("startPoint");
        medicationLoadDetails.setEndPoint("EndPoint");
        medicationLoadDetails.setMedication(medication);
        medicationLoadDetails.setDrone(drone);
        medicationLoadDetails.setTimeToLoad(LocalDateTime.now());
        dbService.setMedicalDeliveryDetails(medicationLoadDetails);
        List<MedicationDeliveryDetails> list = medicationDeliveryDetailsRepository.findAll();
        assertEquals(1, list.size());
        medicationDeliveryDetailsRepository.deleteAll();
        Assertions.assertThat(medicationDeliveryDetailsRepository.findAll()).isEmpty();
    }

    private Drone findDrone(String serialNumber) {
        return dbService.findDroneBySerialNumberBatteryLoad(serialNumber);
    }
}