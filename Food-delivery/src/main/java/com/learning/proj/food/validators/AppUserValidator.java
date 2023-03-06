package com.learning.proj.food.validators;

import com.learning.proj.food.domain.entity.Admin;
import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.Customer;
import com.learning.proj.food.domain.entity.Rider;
import com.learning.proj.food.exception.chef.ChefMissingParameterException;
import com.learning.proj.food.exception.customer.CustomerMissingParameterException;
import com.learning.proj.food.exception.exceptionType.BadRequestException;
import com.learning.proj.food.exception.rider.RiderMissingParameterException;

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
                if (chef.getRestaurant() == null) {
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
