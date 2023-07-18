package com.droneservice.communicateapi.data.request;

import com.droneservice.communicateapi.util.DroneState;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneRegisterRequest {

    @NotBlank
    @NotNull
    @Size(max = 100)
    private String serialNumber;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private String model;

    @NotNull
    @Max(500)
    private double weightLimit;

    @NotNull
    private BigDecimal batteryCapacity;

    @NotBlank
    @NotNull
    @Size(max = 50)
    private DroneState state;
}
