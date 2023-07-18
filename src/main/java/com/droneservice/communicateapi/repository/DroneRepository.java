package com.droneservice.communicateapi.repository;

import com.droneservice.communicateapi.entity.Drone;
import com.droneservice.communicateapi.util.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    List<Drone> findAllByState(@Param("state") DroneState idle);

    @Query(value = "select d.* from t_drone d where d.serial_number =:serialNumber ", nativeQuery = true)
    Drone findBySerialNumber(@Param("serialNumber") String serialNumber);
}
