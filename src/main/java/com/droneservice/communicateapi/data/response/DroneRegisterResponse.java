package com.droneservice.communicateapi.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneRegisterResponse {

    private String result;
    private String serialNumber;
    private String message;
    private LocalDateTime timestamp;
}
