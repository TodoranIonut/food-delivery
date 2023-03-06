package com.gateway.gatewaysecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    private String foodId;
    private String foodName;
    private Integer preparationMinutes;
    private String description;
    private Integer weightGrams;
    private URL pictureUrl;
    private Restaurant restaurant;
    private Set<OrderItem> orderItem;
}