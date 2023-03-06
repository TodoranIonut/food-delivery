package com.example.appusercomponent.controller.mappers;

import com.example.appusercomponent.controller.dto.user.DeliveryAreaDTO;
import com.example.appusercomponent.controller.dto.user.LocationDTO;
import com.example.appusercomponent.domain.entity.DeliveryArea;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryAreaMapper {

    DeliveryArea deliveryAreaDToToDeliverArea(DeliveryAreaDTO deliveryAreaDTO);

    DeliveryArea locationDTOToDeliveryArea(LocationDTO locationDTO);
}
