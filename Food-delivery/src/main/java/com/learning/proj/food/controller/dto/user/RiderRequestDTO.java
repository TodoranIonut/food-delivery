package com.learning.proj.food.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderRequestDTO extends UserInfoRequestDTO {

    private String vehicle;
    private Double longitude;
    private Double latitude;
}
