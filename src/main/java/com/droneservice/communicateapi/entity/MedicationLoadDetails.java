package com.droneservice.communicateapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_medication_load_details")
public class MedicationLoadDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "stu_seq", sequenceName = "t_medication_load_details_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stu_seq")
    @Column(name = "id_load_details")
    private Integer idLoadDetails;

    @Column(name = "start_point", columnDefinition = "VARCHAR(50) NOT NULL")
    private String startPoint;

    @Column(name = "end_point", columnDefinition = "VARCHAR(50) NOT NULL")
    private String endPoint;

    @Column(name = "time_to_load", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime timeToLoad;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_serial_number", referencedColumnName = "serial_number")
    private Drone drone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_code", referencedColumnName = "code", unique = true)
    private Medication medication;
}
