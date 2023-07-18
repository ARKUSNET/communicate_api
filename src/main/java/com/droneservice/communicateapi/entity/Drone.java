package com.droneservice.communicateapi.entity;

import com.droneservice.communicateapi.util.DroneState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_drone")
public class Drone implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "serial_number", columnDefinition = "VARCHAR(100) NOT NULL")
    private String serialNumber;

    @Column(name = "model", columnDefinition = "VARCHAR(50) NOT NULL")
    private String model;

    @Column(name = "weight_limit", columnDefinition = "VARCHAR(5) NOT NULL")
    private double weightLimit;

    @Column(name = "battery_capacity",precision = 3, scale = 2)
    private BigDecimal batteryCapacity;

    @Column(name = "state", columnDefinition = "VARCHAR(50) NOT NULL")
    private DroneState state;
}
