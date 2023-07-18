package com.droneservice.communicateapi.controller;

import com.droneservice.communicateapi.data.request.*;
import com.droneservice.communicateapi.data.response.*;
import com.droneservice.communicateapi.exception.DroneSrvRuntimeException;
import com.droneservice.communicateapi.service.DroneServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.droneservice.communicateapi.util.Utils.isEmptyString;

@RestController
@RequestMapping(path = "/api/drone")
@Validated
@Slf4j
public class DroneController {

    private final DroneServiceApi droneService;

    @Autowired
    public DroneController(DroneServiceApi droneService) {
        this.droneService = droneService;
    }

    @PostMapping(path = "/register/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneRegisterResponse> registerDrone(
            @Valid @NotNull @RequestBody DroneRegisterRequest droneRegisterRequest) {
        DroneRegisterResponse newDrone = droneService.registerDrone(droneRegisterRequest);
        return new ResponseEntity<>(newDrone, HttpStatus.CREATED);
    }

    @PostMapping(path = "/register/medication", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MedicationRegisterResponse> registerMedication(
            @Valid @NotNull @RequestBody MedicationRegisterRequest medicationRegisterRequest) {
        MedicationRegisterResponse newDrone = droneService.registerMedication(medicationRegisterRequest);
        return new ResponseEntity<>(newDrone, HttpStatus.CREATED);
    }

    @GetMapping(path = "/available", produces = "application/json")
    public ResponseEntity<AvailableDroneResponse> getAvailableDronesForLoading() {
        AvailableDroneResponse drones = droneService.getAvailableDrones();
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }

    @PostMapping(path = "/battery-level", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneBatteryLevelResponse> checkDroneBatteryLevel(
            @NotNull @RequestBody() DroneBatteryLevelRequest droneBatteryLevelRequest) {
        if (isEmptyString(droneBatteryLevelRequest.getSerialNumber())) {
            throw new DroneSrvRuntimeException("SerialNumber is Required");
        }
        DroneBatteryLevelResponse droneBattery = droneService.getBatteryLevel(droneBatteryLevelRequest);
        return new ResponseEntity<>(droneBattery, HttpStatus.CREATED);
    }

    @PostMapping(path = "/load", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneMedicationLoadResponse> loadDroneWithMedication(@Valid @NotNull @RequestBody DroneMedicationLoadRequest droneMedicationLoadRequest) {
        DroneMedicationLoadResponse droneMedicationLoadResponse = droneService.droneMedicationLoad(droneMedicationLoadRequest);
        return new ResponseEntity<>(droneMedicationLoadResponse, HttpStatus.CREATED);
    }

    @PostMapping(path = "/deliver", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneMedicalDeliveryResponse> droneMedicalLoadDelivery(@NotNull @RequestBody DroneMedicalDeliveryRequest droneMedicalDeliveryRequest) {
        DroneMedicalDeliveryResponse droneMedicalDeliveryResponse = droneService.droneMedicalDelivery(droneMedicalDeliveryRequest);
        return new ResponseEntity<>(droneMedicalDeliveryResponse, HttpStatus.CREATED);
    }

    @PostMapping(path = "/details", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneDetailsResponse> droneDetails(@Valid @NotNull @RequestBody DroneDetailsRequest droneDetailsRequest) {
        DroneDetailsResponse drone = droneService.droneDetails(droneDetailsRequest);
        return new ResponseEntity<>(drone, HttpStatus.OK);
    }
}
