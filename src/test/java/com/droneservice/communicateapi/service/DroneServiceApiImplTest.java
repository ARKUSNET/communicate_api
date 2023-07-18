package com.droneservice.communicateapi.service;

import com.droneservice.communicateapi.CommunicateApiConfig;
import com.droneservice.communicateapi.data.request.*;
import com.droneservice.communicateapi.data.response.*;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.entity.Medication;
import com.droneservice.communicateapi.entity.MedicationDeliveryDetails;
import com.droneservice.communicateapi.entity.MedicationLoadDetails;
import com.droneservice.communicateapi.util.DroneState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CommunicateApiConfig.class})
@DataJpaTest
@ActiveProfiles({"test"})
class DroneServiceApiImplTest {

    @InjectMocks
    private DroneServiceApiImpl droneService;

    @Mock
    private DbService dbService;

    @BeforeEach
    public void init() {
        Medication medication = new Medication("WER3E", "Aspirin", 25.0, "Aspirin.jpeg");
        Drone drone = new Drone("QWER123KF", "Samsung", 134.0, new BigDecimal(0.95), DroneState.IDLE);
        Drone drone2 = new Drone("QWEDS123KF", "Samsung", 136.0, new BigDecimal(0.96), DroneState.IDLE);
        Drone drone3 = new Drone("QFER123KF", "Samsung", 132.0, new BigDecimal(0.93), DroneState.IDLE);
        MedicationLoadDetails medicationLoadDetails = new MedicationLoadDetails(1, "startPoint", "EndPoint",
                LocalDateTime.now(), drone, medication);
        MedicationDeliveryDetails medicationDeliveryDetails = new MedicationDeliveryDetails(1, LocalDateTime.now(), medicationLoadDetails);
        List<Drone> list = new ArrayList<>();
        list.add(drone);
        list.add(drone2);
        list.add(drone3);
        DroneRegisterRequest droneRegisterRequest = new DroneRegisterRequest();
        MedicationRegisterRequest medicationRegisterRequest = new MedicationRegisterRequest();
        when(dbService.findAllAvailableDrones()).thenReturn(list);
        when(dbService.findDroneBySerialNumberBatteryLoad("QWER123KF")).thenReturn(drone);
        when(dbService.findDroneBySerialNumberDelivery("QWEDS123KF")).thenReturn(drone2);
        when(dbService.findDroneBySerialNumberLoad("QWER123KF")).thenReturn(drone);
        when(dbService.saveNewDrone(droneRegisterRequest)).thenReturn(drone);
        when(dbService.findMedicationByCode("WER3E")).thenReturn(medication);
        when(dbService.findMedicationLoadDetailsByCode("WER3E")).thenReturn(medicationLoadDetails);
        when(dbService.findMedicationLoadDetailsBySerialNumber("QWER123KF")).thenReturn(medicationLoadDetails);
        when(dbService.saveNewMedication(medicationRegisterRequest)).thenReturn(medication);
    }

    @Test
    void registerDrone() {
        DroneRegisterRequest droneRegisterRequest = new DroneRegisterRequest();
        DroneRegisterResponse droneRegisterResponse = droneService.registerDrone(droneRegisterRequest);
        Assertions.assertThat(droneRegisterResponse).extracting(DroneRegisterResponse::getSerialNumber).isEqualTo("QWER123KF");
        verify(dbService, times(1)).saveNewDrone(droneRegisterRequest);
    }

    @Test
    void registerMedication() {
        MedicationRegisterRequest medicationRegisterRequest = new MedicationRegisterRequest();
        MedicationRegisterResponse medicationRegisterResponse = droneService.registerMedication(medicationRegisterRequest);
        Assertions.assertThat(medicationRegisterResponse).extracting(MedicationRegisterResponse::getCode).isEqualTo("WER3E");
        verify(dbService, times(1)).saveNewMedication(medicationRegisterRequest);
    }

    @Test
    void getAvailableDrones() {
        AvailableDroneResponse availableDroneResponse = droneService.getAvailableDrones();
        assertEquals(3, availableDroneResponse.getDrones().size());
        verify(dbService, times(1)).findAllAvailableDrones();
    }

    @Test
    void getBatteryLevel() {
        DroneBatteryLevelRequest droneBatteryLevelRequest = new DroneBatteryLevelRequest();
        droneBatteryLevelRequest.setSerialNumber("QWER123KF");
        DroneBatteryLevelResponse batteryLevelResponse = droneService.getBatteryLevel(droneBatteryLevelRequest);
        Assertions.assertThat(batteryLevelResponse).extracting(DroneBatteryLevelResponse::getSerialNumber).isEqualTo("QWER123KF");
        verify(dbService, times(1)).findDroneBySerialNumberBatteryLoad("QWER123KF");
    }
//
//    @Test
//    void droneMedicationLoad() {
//        DroneMedicationLoadRequest droneMedicationLoadRequest = new DroneMedicationLoadRequest();
//        DroneMedicationLoadResponse droneMedicationLoadResponse = droneService.droneMedicationLoad(droneMedicationLoadRequest);
//        Assertions.assertThat(droneMedicationLoadResponse).extracting(DroneMedicationLoadResponse::getSerialNumber).isEqualTo("QWER123KF");
//        verify(dbService, times(1)).findMedicationLoadDetailsBySerialNumber("QWER123KF");
//    }
//
//    @Test
//    void droneMedicalDelivery() {
//        DroneMedicalDeliveryRequest droneMedicalDeliveryRequest = new DroneMedicalDeliveryRequest();
//        DroneMedicalDeliveryResponse droneMedicalDeliveryResponse = droneService.droneMedicalDelivery(droneMedicalDeliveryRequest);
//        Assertions.assertThat(droneMedicalDeliveryResponse).extracting(DroneMedicalDeliveryResponse::getSerialNumber).isEqualTo("QWER123KF");
//        verify(dbService, times(1)).findDroneBySerialNumberDelivery("QWER123KF");
//    }
}