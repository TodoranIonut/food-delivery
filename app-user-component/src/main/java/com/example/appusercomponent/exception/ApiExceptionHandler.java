package com.example.appusercomponent.exception;

import com.example.appusercomponent.exception.exceptionType.BadRequestException;
import com.example.appusercomponent.exception.exceptionType.ConflictException;
import com.example.appusercomponent.exception.exceptionType.ForbiddenException;
import com.example.appusercomponent.exception.exceptionType.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handleApiConflictException(DeliveryApplicationException e) {
        //1. create payload details
        HttpStatus badRequest = HttpStatus.CONFLICT;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );

        //2.return response entity
        return new ResponseEntity<>(exceptionData, badRequest);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(DeliveryApplicationException e) {
        //1. create payload details
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );

        //2.return response entity
        return new ResponseEntity<>(exceptionData, badRequest);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleApiBadRequestException(DeliveryApplicationException e) {
        //1. create payload details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );

        //2.return response entity
        return new ResponseEntity<>(exceptionData, badRequest);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleApiForbiddenException(DeliveryApplicationException e) {
        //1. create payload details
        HttpStatus badRequest = HttpStatus.FORBIDDEN;
        ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );

        //2.return response entity
        return new ResponseEntity<>(exceptionData, badRequest);
    }
}
