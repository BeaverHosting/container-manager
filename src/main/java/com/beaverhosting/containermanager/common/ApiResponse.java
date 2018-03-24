package com.beaverhosting.containermanager.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jboss.logging.Field;
import org.springframework.stereotype.Component;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse {
    String name;
    String status;
    String stdout;
    String stderr;
}
