package com.beaverhosting.containermanager.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ContainerWriteDto {
    @NotBlank(message = "{name.required}")
    private String name;
}
