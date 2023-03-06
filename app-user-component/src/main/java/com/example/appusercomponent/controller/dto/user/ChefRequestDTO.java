package com.example.appusercomponent.controller.dto.user;

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
public class ChefRequestDTO extends UserInfoRequestDTO {

    private String restaurantId;

}
