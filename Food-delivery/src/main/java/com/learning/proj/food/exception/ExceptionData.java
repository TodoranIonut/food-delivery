package com.learning.proj.food.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionData {

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime dateTime;
}
