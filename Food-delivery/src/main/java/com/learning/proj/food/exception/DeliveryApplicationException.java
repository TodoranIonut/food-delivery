package com.learning.proj.food.exception;

public class DeliveryApplicationException extends Throwable {

    public DeliveryApplicationException(String message) {
        super(message);
    }

    public DeliveryApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
