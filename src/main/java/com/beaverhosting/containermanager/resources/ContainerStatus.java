package com.beaverhosting.containermanager.resources;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jboss.logging.Field;
import org.springframework.stereotype.Component;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContainerStatus {
    private String name;
    private String status;
    private String stdout;
    private String stderr;
}
