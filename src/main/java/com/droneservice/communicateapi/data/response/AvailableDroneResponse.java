package com.droneservice.communicateapi.data.response;

import com.droneservice.communicateapi.entity.Drone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDroneResponse {

    private String status;
    private LocalDateTime timestamp;
    private List<Drone> drones;
}
