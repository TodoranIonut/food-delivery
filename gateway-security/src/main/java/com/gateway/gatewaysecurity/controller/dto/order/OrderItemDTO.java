package com.gateway.gatewaysecurity.controller.dto.order;

import lombok.Data;

import java.net.URL;

@Data
public class OrderItemDTO {

    private String foodName;
    private Integer preparationMinutes;
    private String description;
    private Integer weightGrams;
    private URL pictureUrl;
    private short amount;
}
