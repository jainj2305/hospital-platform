package com.hospital.patient_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PatientResponse {

    private UUID id;
    private String name;
    private Integer age;
    private String gender;
    private String phone;
}
