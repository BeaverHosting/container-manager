package com.beaverhosting.containermanager.controllers.exceptions;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionMessage {
    String date;
    String path;
    String className;
    String message;
}
