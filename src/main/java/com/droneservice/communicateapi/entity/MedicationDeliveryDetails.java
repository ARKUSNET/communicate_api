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
@Table(name = "t_medication_delivery_details")
public class MedicationDeliveryDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "stu_med_seq", sequenceName = "t_medication_delivery_details_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stu_med_seq")
    @Column(name = "id_delivery_details")
    private Integer idDeliveryDetails;

    @Column(name = "delivered_time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime deliveredTime;

    @OneToOne(targetEntity = MedicationLoadDetails.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_load_details", referencedColumnName = "id_load_details")
    private MedicationLoadDetails medicationLoadDetails;
}
