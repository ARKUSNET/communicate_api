package com.droneservice.communicateapi.service;

import com.droneservice.communicateapi.data.request.*;
import com.droneservice.communicateapi.data.response.*;
import com.droneservice.communicateapi.exception.DroneSrvRuntimeException;
import org.springframework.stereotype.Component;

@Component
public interface DroneServiceApi {

    DroneRegisterResponse registerDrone(DroneRegisterRequest newDrone);

    MedicationRegisterResponse registerMedication(MedicationRegisterRequest newMedication);

    AvailableDroneResponse getAvailableDrones();

    DroneBatteryLevelResponse getBatteryLevel(DroneBatteryLevelRequest batteryLevel) throws DroneSrvRuntimeException;

    DroneMedicationLoadResponse droneMedicationLoad(DroneMedicationLoadRequest medicationLoad) throws DroneSrvRuntimeException;

    DroneMedicalDeliveryResponse droneMedicalDelivery(DroneMedicalDeliveryRequest medicalDelivery) throws DroneSrvRuntimeException;

    DroneDetailsResponse droneDetails(DroneDetailsRequest droneDetailsRequest);
}
