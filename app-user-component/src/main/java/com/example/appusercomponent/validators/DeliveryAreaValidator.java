package com.example.appusercomponent.validators;

import com.example.appusercomponent.domain.entity.DeliveryArea;
import com.example.appusercomponent.exception.deliveryArea.DeliveryAreaCoordinatesOutOfRangeException;

import java.util.ArrayList;
import java.util.List;


public class DeliveryAreaValidator {

    /**
     * latitude should be between -90 and 90 degrees
     * longitude should be between -180 and 180 degrees
     * radius should be no longer then 30km or 3000m
     *
     * @throws DeliveryAreaCoordinatesOutOfRangeException
     */
    public static void validate(DeliveryArea deliveryArea) throws DeliveryAreaCoordinatesOutOfRangeException {

        List<String> messageList = new ArrayList<>();

        if (deliveryArea.getRadius() != null) {
            if (deliveryArea.getRadius() > 30000) {
                messageList.add("range is too big");
            }
            if (deliveryArea.getRadius() < 0) {
                messageList.add("range cannot be negative");
            }
        }

        if (deliveryArea.getLatitude() > 90.0) {
            messageList.add("latitude cannot exceed 90 degrees");
        }

        if (deliveryArea.getLatitude() < -90.0) {
            messageList.add("latitude cannot be less then -90 degrees");
        }

        if (deliveryArea.getLongitude() > 180.0) {
            messageList.add("longitude cannot exceed 180 degrees");
        }

        if (deliveryArea.getLongitude() < -180.0) {
            messageList.add("longitude cannot be less then -180 degrees");
        }

        if (messageList.size() > 0) {
            String exceptionMessage = String.join(" and ", messageList);
            throw new DeliveryAreaCoordinatesOutOfRangeException(exceptionMessage);
        }
    }
}
