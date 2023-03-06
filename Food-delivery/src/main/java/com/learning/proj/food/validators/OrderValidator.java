package com.learning.proj.food.validators;

import com.learning.proj.food.domain.entity.Order;
import com.learning.proj.food.exception.order.OrderMissingParameterException;

import java.util.ArrayList;
import java.util.List;

public class OrderValidator {

    /**
     * Order fields cannot be null
     * Fields null exceptions are: chefId, riderId, deliveryEstimation
     */
    public static void validate(Order order) throws OrderMissingParameterException {
        List<String> messageList = new ArrayList<>();

        if(order.getOrderItems() == null) {
            messageList.add("orderItems");
        }
        if(order.getDeliveryArea() == null) {
            messageList.add("deliveryArea");
        }
        if(order.getRestaurant() == null) {
            messageList.add("restaurant");
        }
        if(order.getOrderStatus() == null) {
            messageList.add("orderStatus");
        }

        if(messageList.size() > 0){
            String exceptionMessage = String.join(", ",messageList);
            throw new OrderMissingParameterException(exceptionMessage);
        }
    }
}
