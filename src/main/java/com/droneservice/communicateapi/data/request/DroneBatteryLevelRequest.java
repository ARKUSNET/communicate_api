package com.droneservice.communicateapi.data.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneBatteryLevelRequest {


    @NotBlank
    @NotNull
    @Size(max = 100)
    private String serialNumber;
}
