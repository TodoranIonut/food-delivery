package com.gateway.gatewaysecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private String restaurantId;
    private String restaurantName;
    private DeliveryArea deliveryArea;
    private Collection<Food> menu;
    private Set<Chef> chefs;
}