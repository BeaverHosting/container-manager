package com.beaverhosting.containermanager.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ContainerCreationForm {

    @NotBlank(message = "{name.required}")
    private String name;
}
