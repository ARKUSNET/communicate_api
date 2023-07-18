package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.entity.MedicationDeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationDeliveryDetailsRepository extends JpaRepository<MedicationDeliveryDetails, Integer> {
}