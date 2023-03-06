package com.learning.proj.food.exception.rider;

import com.learning.proj.food.exception.exceptionType.ConflictException;

public class RiderPhoneNumberConflictException extends ConflictException {

    public RiderPhoneNumberConflictException(String riderPhoneNumber) {
        super(String.format("rider phone number %s is unavailable", riderPhoneNumber));
    }
}
