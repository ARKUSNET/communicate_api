package com.droneservice.communicateapi.data.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationRegisterRequest {

    @NotBlank
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Invalid Input - allowed only upper case letters, underscore and numbers")
    private String code;

    @NotBlank
    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[\\w_-]*$", message = "Invalid Input - allowed only letters, numbers, ‘-‘, ‘_’")
    private String name;

    @NotNull
    private double weight;

    private String image;
}
