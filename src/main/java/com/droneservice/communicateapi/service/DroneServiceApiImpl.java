package com.droneservice.communicateapi.service;

import com.droneservice.communicateapi.data.request.*;
import com.droneservice.communicateapi.data.response.*;
import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.entity.Medication;
import com.droneservice.communicateapi.entity.MedicationLoadDetails;
import com.droneservice.communicateapi.exception.DroneSrvRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import static com.droneservice.communicateapi.util.Utils.FAILED;
import static com.droneservice.communicateapi.util.Utils.SUCCESS;

@Service
@Slf4j
public class DroneServiceApiImpl implements DroneServiceApi {

    private final DbService dbService;

    public DroneServiceApiImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public DroneRegisterResponse registerDrone(DroneRegisterRequest newDrone) {
        Drone drone = dbService.saveNewDrone(newDrone);
        DroneRegisterResponse droneResponse = new DroneRegisterResponse();
        droneResponse.setResult(SUCCESS);
        droneResponse.setSerialNumber(drone.getSerialNumber());
        droneResponse.setMessage("New Drone created successfully");
        droneResponse.setTimestamp(LocalDateTime.now());
        return droneResponse;
    }

    @Override
    public MedicationRegisterResponse registerMedication(MedicationRegisterRequest newMedication) {
        Medication medication = dbService.saveNewMedication(newMedication);
        MedicationRegisterResponse medicationRegisterResponse = new MedicationRegisterResponse();
        medicationRegisterResponse.setResult(SUCCESS);
        medicationRegisterResponse.setCode(medication.getCode());
        medicationRegisterResponse.setMessage("New Medication created successfully");
        medicationRegisterResponse.setTimestamp(LocalDateTime.now());
        return medicationRegisterResponse;
    }

    @Override
    public AvailableDroneResponse getAvailableDrones() {
        List<Drone> drones = dbService.findAllAvailableDrones();
        return new AvailableDroneResponse("status", LocalDateTime.now(), drones);
    }

    @Override
    public DroneBatteryLevelResponse getBatteryLevel(DroneBatteryLevelRequest batteryLevel) throws DroneSrvRuntimeException {
        Drone drone = dbService.findDroneBySerialNumberBatteryLoad(batteryLevel.getSerialNumber());
        DecimalFormat decFormat = new DecimalFormat("#%");
        DroneBatteryLevelResponse droneBatteryLevelResponse = new DroneBatteryLevelResponse();
        droneBatteryLevelResponse.setStatus(SUCCESS);
        droneBatteryLevelResponse.setSerialNumber(drone.getSerialNumber());
        droneBatteryLevelResponse.setBatteryCapacity(decFormat.format(drone.getBatteryCapacity()));
        droneBatteryLevelResponse.setTimestamp(LocalDateTime.now());
        return droneBatteryLevelResponse;
    }

    @Override
    public DroneMedicationLoadResponse droneMedicationLoad(DroneMedicationLoadRequest medicationLoad) throws DroneSrvRuntimeException {
        Drone drone = dbService.findDroneBySerialNumberLoad(medicationLoad.getSerialNumber());
        Medication medication = dbService.findMedicationByCode(medicationLoad.getCode());
        if (drone.getWeightLimit() < medication.getWeight()) {
            throw new DroneSrvRuntimeException("The Drone cannot load more than the weight limit");
        }
        dbService.setProcessLoading(drone);
        dbService.setMedicationLoadDetailsToDrone(drone, medication, medicationLoad);
        dbService.setProcessLoaded(drone);
        DroneMedicationLoadResponse droneMedicationLoadResponse = new DroneMedicationLoadResponse();
        droneMedicationLoadResponse.setResult(SUCCESS);
        droneMedicationLoadResponse.setSerialNumber(medicationLoad.getSerialNumber());
        droneMedicationLoadResponse.setMessage("Drone Loaded successfully");
        droneMedicationLoadResponse.setTimestamp(LocalDateTime.now());
        droneMedicationLoadResponse.setMedication(medication);
        return droneMedicationLoadResponse;
    }

    @Override
    public DroneMedicalDeliveryResponse droneMedicalDelivery(DroneMedicalDeliveryRequest medicalDelivery) throws DroneSrvRuntimeException {
        MedicationLoadDetails medicationLoadDetails = dbService.findMedicationLoadDetailsBySerialNumber(medicalDelivery.getSerialNumber());
        Drone drone = dbService.findDroneBySerialNumberDelivery(medicalDelivery.getSerialNumber());
        dbService.setProcessDelivering(drone);
        dbService.setMedicalDeliveryDetails(medicationLoadDetails);
        dbService.setProcessDelivered(drone);
        DroneMedicalDeliveryResponse deliverDroneResponse = new DroneMedicalDeliveryResponse();
        deliverDroneResponse.setResult(SUCCESS);
        deliverDroneResponse.setSerialNumber(medicalDelivery.getSerialNumber());
        deliverDroneResponse.setMessage("Delivered successfully");
        deliverDroneResponse.setTimestamp(LocalDateTime.now());
        return deliverDroneResponse;
    }

    @Override
    public DroneDetailsResponse droneDetails(DroneDetailsRequest droneDetailsRequest) {
        Drone drone = dbService.findDroneBySerialNumber(droneDetailsRequest.getSerialNumber());
        DroneDetailsResponse droneDetailsResponse = new DroneDetailsResponse();
        droneDetailsResponse.setTimestamp(LocalDateTime.now());
        if(drone != null) {
            droneDetailsResponse.setSerialNumber(drone.getSerialNumber());
            droneDetailsResponse.setResult(SUCCESS);
            droneDetailsResponse.setMessage("Drone details info");
        } else {
            droneDetailsResponse.setSerialNumber(droneDetailsRequest.getSerialNumber());
            droneDetailsResponse.setResult(FAILED);
            droneDetailsResponse.setMessage("Drone details info not exist");
        }
        return droneDetailsResponse;
    }
}
