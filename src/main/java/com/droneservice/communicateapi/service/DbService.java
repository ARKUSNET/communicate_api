package com.droneservice.communicateapi.service;

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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DbService {

    private final DroneRepository droneRepository;

    private final MedicationRepository medicationRepository;

    private final MedicationDeliveryDetailsRepository medicationDeliveryDetailsRepository;

    private final MedicationLoadDetailsRepository medicationLoadDetailsRepository;

    public DbService(DroneRepository droneRepository, MedicationRepository medicationRepository, MedicationDeliveryDetailsRepository medicationDeliveryDetailsRepository,
                     MedicationLoadDetailsRepository medicationLoadDetailsRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
        this.medicationDeliveryDetailsRepository = medicationDeliveryDetailsRepository;
        this.medicationLoadDetailsRepository = medicationLoadDetailsRepository;
    }

    public Drone saveNewDrone(DroneRegisterRequest newDrone) {
        Drone drone = new Drone();
        drone.setSerialNumber(newDrone.getSerialNumber());
        drone.setModel(newDrone.getModel());
        drone.setWeightLimit(newDrone.getWeightLimit());
        drone.setBatteryCapacity(newDrone.getBatteryCapacity());
        drone.setState(newDrone.getState());
        droneRepository.save(drone);
        return drone;
    }

    public Medication saveNewMedication(MedicationRegisterRequest newMedication) {
        Medication medication = new Medication();
        medication.setCode(newMedication.getCode());
        medication.setName(newMedication.getName());
        medication.setWeight(newMedication.getWeight());
        medication.setImage(newMedication.getImage());
        medicationRepository.save(medication);
        return medication;
    }

    public List<Drone> findAllAvailableDrones() {
        return droneRepository.findAllByState(DroneState.IDLE);
    }

    public Drone findDroneBySerialNumberBatteryLoad(String serialNumber) {
        return findDroneBySerialNumber(serialNumber);
    }

    public Drone findDroneBySerialNumberLoad(String serialNumber) {
        return findDroneBySerialNumber(serialNumber);
    }

    public Drone findDroneBySerialNumberDelivery(String serialNumber) {
        return findDroneBySerialNumber(serialNumber);
    }

    public Medication findMedicationByCode(String code) {
        return medicationRepository.findByCode(code);
    }

    public MedicationLoadDetails findMedicationLoadDetailsByCode(String code) {
        return medicationLoadDetailsRepository.findByCode(code);
    }

    public MedicationLoadDetails findMedicationLoadDetailsBySerialNumber(String serialNumber) {
        return medicationLoadDetailsRepository.findByDrone(serialNumber);
    }

    public void setProcessLoading(Drone drone) {
        drone.setState(DroneState.LOADING);
        droneRepository.saveAndFlush(drone);
    }

    public void setProcessLoaded(Drone drone) {
        drone.setState(DroneState.LOADED);
        droneRepository.saveAndFlush(drone);
    }

    public void setProcessDelivering(Drone drone) {
        drone.setState(DroneState.DELIVERING);
        droneRepository.saveAndFlush(drone);
    }

    public void setProcessDelivered(Drone drone) {
        drone.setState(DroneState.DELIVERED);
        droneRepository.saveAndFlush(drone);
    }

    public void setMedicationLoadDetailsToDrone(Drone drone, Medication medication, DroneMedicationLoadRequest medicationLoad) {
        MedicationLoadDetails medicationLoadDetails = new MedicationLoadDetails();
        medicationLoadDetails.setDrone(drone);
        medicationLoadDetails.setMedication(medication);
        medicationLoadDetails.setStartPoint(medicationLoad.getStartPoint());
        medicationLoadDetails.setEndPoint(medicationLoad.getEndPoint());
        medicationLoadDetails.setTimeToLoad(LocalDateTime.now());
        medicationLoadDetailsRepository.save(medicationLoadDetails);
    }

    public void setMedicalDeliveryDetails(MedicationLoadDetails medicationLoadDetails) {
        MedicationDeliveryDetails medicationDeliveryDetails = new MedicationDeliveryDetails();
        medicationDeliveryDetails.setMedicationLoadDetails(medicationLoadDetails);
        medicationDeliveryDetails.setDeliveredTime(LocalDateTime.now());
        medicationDeliveryDetailsRepository.save(medicationDeliveryDetails);
    }

    public Drone findDroneBySerialNumber(String serialNumber) {
        return droneRepository.findBySerialNumber(serialNumber);
    }
}
