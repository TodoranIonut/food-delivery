package com.gateway.gatewaysecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryArea {

    private String areaId;
    private Double latitude;
    private Double longitude;
    private Integer radius;
    private Restaurant restaurant;

    public DeliveryArea(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
