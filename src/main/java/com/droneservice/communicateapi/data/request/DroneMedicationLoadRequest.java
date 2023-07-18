package com.droneservice.communicateapi.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneMedicationLoadRequest {

    @NotNull
    @NotBlank
    private String startPoint;

    @NotNull
    @NotBlank
    private String endPoint;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String serialNumber;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Invalid Input - allowed only upper case letters, underscore and numbers")
    private String code;
}
