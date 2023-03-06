package com.learning.proj.food.service.order;

import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Order;
import com.learning.proj.food.domain.entity.OrderStatus;
import com.learning.proj.food.exception.DeliveryApplicationException;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderService {

    Order findOrderById(String orderId) throws DeliveryApplicationException;

    Order findOrderByRiderAndStatus(OrderStatus orderStatus) throws DeliveryApplicationException;

    Set<Order> findOrderByRestaurant(String restaurantId) throws DeliveryApplicationException;

    Set<Order> findOrderByRestaurantAndStatus(String restaurantId, OrderStatus orderStatus) throws DeliveryApplicationException;

    Order findReadyForDeliveryOrderInDeliveryArea(DeliveryArea deliveryArea) throws DeliveryApplicationException;

    Order addNewOrder(Order order) throws DeliveryApplicationException;

    Order updateOrder(String orderId, Order order) throws DeliveryApplicationException;

    Order updateOrderStatus(String orderId, OrderStatus orderStatus) throws DeliveryApplicationException;

    LocalDateTime estimateDeliveryTime(String orderId) throws DeliveryApplicationException;
}