package com.droneservice.communicateapi.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDetailsRequest {

    @NotBlank
    @NotNull
    @Size(max = 100)
    private String serialNumber;
}
