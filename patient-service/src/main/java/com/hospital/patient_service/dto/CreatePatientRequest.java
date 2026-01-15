package com.hospital.patient_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePatientRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer age;

    @NotBlank
    private String gender;

    @NotBlank
    private String phone;
}
