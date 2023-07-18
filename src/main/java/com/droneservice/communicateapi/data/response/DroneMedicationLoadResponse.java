package com.droneservice.communicateapi.data.response;

import com.droneservice.communicateapi.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneMedicationLoadResponse {

    private String result;
    private String serialNumber;
    private String message;
    private LocalDateTime timestamp;
    private Medication medication;
}
