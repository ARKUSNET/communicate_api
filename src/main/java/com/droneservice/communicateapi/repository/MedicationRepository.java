package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {

    @Query(value = "select m.* from t_medication m where m.code =:code ", nativeQuery = true)
    Medication findByCode(@Param("code") String code);
}