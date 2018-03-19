package com.beaverhosting.containermanager.resources;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Builder
@Data
public class ContainerStatus {
    private String name;
    private String status;
    private String stdout;
    private String stderr;
}
