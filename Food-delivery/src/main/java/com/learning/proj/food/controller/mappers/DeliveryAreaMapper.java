package com.learning.proj.food.controller.mappers;

import com.learning.proj.food.controller.dto.restaurant.DeliveryAreaDTO;
import com.learning.proj.food.controller.dto.restaurant.LocationDTO;
import com.learning.proj.food.domain.entity.DeliveryArea;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryAreaMapper {

    DeliveryArea deliveryAreaDToToDeliverArea(DeliveryAreaDTO deliveryAreaDTO);

    DeliveryArea locationDTOToDeliveryArea(LocationDTO locationDTO);
}
