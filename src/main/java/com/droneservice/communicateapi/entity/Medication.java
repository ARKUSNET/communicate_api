package com.droneservice.communicateapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_medication")
public class Medication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code", columnDefinition = "VARCHAR(50) NOT NULL")
    private String code;

    @Column(name = "name", columnDefinition = "VARCHAR(50) NOT NULL")
    private String name;

    @Column(name = "weight", columnDefinition = "VARCHAR(10) NOT NULL")
    private double weight;

    @Column(name = "image")
    private String image;
}
