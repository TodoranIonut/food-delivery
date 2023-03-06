package com.learning.proj.food.exception.rider;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class RiderEmailConflictException extends ConflictException {

    public RiderEmailConflictException(String riderEmail) {
        super(String.format("rider email %s is unavailable", riderEmail));
    }
}
