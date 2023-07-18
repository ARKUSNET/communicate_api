package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.entity.MedicationLoadDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationLoadDetailsRepository extends JpaRepository<MedicationLoadDetails, Integer> {

    @Query(value = "select m from MedicationLoadDetails m where m.medication.code = :code ")
    MedicationLoadDetails findByCode(@Param("code") String code);

    @Query(value = "select m from MedicationLoadDetails m where m.drone.serialNumber = :serialNumber ")
    MedicationLoadDetails findByDrone(@Param("serialNumber") String serialNumber);
}