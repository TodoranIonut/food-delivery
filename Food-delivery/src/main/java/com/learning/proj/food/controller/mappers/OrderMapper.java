package com.learning.proj.food.controller.mappers;

import com.learning.proj.food.controller.dto.order.CreateOrderDTO;
import com.learning.proj.food.controller.dto.order.OrderDTO;
import com.learning.proj.food.controller.dto.order.OrderedFoodDTO;
import com.learning.proj.food.controller.dto.order.ResponseOrderDTO;
import com.learning.proj.food.domain.entity.Order;
import com.learning.proj.food.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "deliveryArea",target = "deliveryArea")
    @Mapping(source = "restaurantId",target = "restaurant.restaurantId")
    @Mapping(source = "orderItems",target = "orderItems")
    Order orderDTOToOrder(OrderDTO orderDTO);

    @Mapping(source = "orderItems",target = "foodItems")
    @Mapping(source = "restaurant.restaurantId",target = "restaurantId")
    ResponseOrderDTO orderToResponseOrderDTO(Order order);

    @Mapping(source = "foodItems",target = "orderItems")
    @Mapping(source = "restaurantId",target = "restaurant.restaurantId")
    Order createdOrderDTOToOrder(CreateOrderDTO createOrderDTO);

    @Mapping(source = "foodId",target = "food.foodId")
    @Mapping(source = "amount",target = "amount")
    OrderItem foodDTOToOrderItem(OrderedFoodDTO orderedFoodDTO);

    @Mapping(source = "food.foodId",target = "foodId")
    OrderedFoodDTO orderItemToFoodDTO(OrderItem orderItem);

    Set<ResponseOrderDTO> orderSetToResponseOrderDTOSet(Set<Order> order);
}
