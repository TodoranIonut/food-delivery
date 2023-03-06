package com.learning.proj.food.controller.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RiderDTO extends UserInfoDTO {

    private String vehicle;
    private Double longitude;
    private Double latitude;
}