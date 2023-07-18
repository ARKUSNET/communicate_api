package com.droneservice.communicateapi.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneBatteryLevelResponse {

    private String status;
    private String serialNumber;
    private String batteryCapacity;
    private LocalDateTime timestamp;
}
