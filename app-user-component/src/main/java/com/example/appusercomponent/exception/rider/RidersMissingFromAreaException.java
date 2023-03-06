package com.example.appusercomponent.exception.rider;


import com.example.appusercomponent.domain.entity.DeliveryArea;
import com.example.appusercomponent.exception.exceptionType.NotFoundException;

public class RidersMissingFromAreaException extends NotFoundException {

    public RidersMissingFromAreaException(DeliveryArea deliveryArea) {
        super(String.format("no riders found in delivery area %s", deliveryArea));
    }
}
