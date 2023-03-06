package com.example.appusercomponent.validators;


import com.example.appusercomponent.domain.entity.*;
import com.example.appusercomponent.exception.chef.ChefMissingParameterException;
import com.example.appusercomponent.exception.customer.CustomerMissingParameterException;
import com.example.appusercomponent.exception.exceptionType.BadRequestException;
import com.example.appusercomponent.exception.rider.RiderMissingParameterException;

import java.util.ArrayList;
import java.util.List;

public class AppUserValidator {

    public static void validate(AppUser appUser) throws BadRequestException {

        List<String> messageList = new ArrayList<>();

        if (appUser.getFirstName() == null) {
            messageList.add("firstName");
        }

        if (appUser.getLastName() == null) {
            messageList.add("lastName");
        }

        if (appUser.getEmail() == null) {
            messageList.add("email");
        }

        if (appUser.getPassword() == null) {
            messageList.add("password");
        }

        if (appUser.getPhoneNumber() == null) {
            messageList.add("phoneNumber");
        }

        if (appUser.getUserInfo() instanceof Customer customer) {
            if (customer.getStreetAddress() == null)
                messageList.add("streetAddress");

            if (messageList.size() > 0) {
                String exceptionMessage = String.join(", ", messageList);
                throw new CustomerMissingParameterException(exceptionMessage);
            }
        } else {

            if (appUser.getUserInfo() instanceof Chef chef) {
                if (chef.getRestaurantId() == null) {
                    messageList.add("restaurant");
                }

                if (messageList.size() > 0) {
                    String exceptionMessage = String.join(", ", messageList);
                    throw new ChefMissingParameterException(exceptionMessage);
                }
            } else {
                if (appUser.getUserInfo() instanceof Rider rider) {
                    if (rider.getVehicle() == null) {
                        messageList.add("vehicle");
                    }

                    if (messageList.size() > 0) {
                        String exceptionMessage = String.join(", ", messageList);
                        throw new RiderMissingParameterException(exceptionMessage);
                    }
                } else {
                    if (appUser.getUserInfo() instanceof Admin && messageList.size() > 0) {
                        String exceptionMessage = String.join(", ", messageList);
                        throw new RiderMissingParameterException(exceptionMessage);
                    }
                }
            }
        }

    }
}
