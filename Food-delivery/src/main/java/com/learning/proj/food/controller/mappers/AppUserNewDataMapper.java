package com.learning.proj.food.controller.mappers;

import com.learning.proj.food.controller.dto.user.AdminRequestDTO;
import com.learning.proj.food.controller.dto.user.AppUserNewDataDTO;
import com.learning.proj.food.controller.dto.user.ChefRequestDTO;
import com.learning.proj.food.controller.dto.user.CustomerRequestDTO;
import com.learning.proj.food.controller.dto.user.RiderRequestDTO;
import com.learning.proj.food.controller.dto.user.UserInfoRequestDTO;
import com.learning.proj.food.domain.entity.Admin;
import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.Customer;
import com.learning.proj.food.domain.entity.Rider;
import com.learning.proj.food.domain.entity.UserInfo;
import com.learning.proj.food.domain.entity.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserNewDataMapper {


    default UserInfo toUserInfo(UserInfoRequestDTO registerUserInfoDTO) {

        if (registerUserInfoDTO instanceof CustomerRequestDTO customerRequestDTO) {
            return customerRequestDTOToCustomer(customerRequestDTO);
        } else {
            if (registerUserInfoDTO instanceof ChefRequestDTO chefRequestDTO) {
                return chefRequestDTOToChef(chefRequestDTO);
            } else {
                if (registerUserInfoDTO instanceof RiderRequestDTO riderRequestDTO) {
                    return riderRequestDTOToRider(riderRequestDTO);
                } else {
                    if (registerUserInfoDTO instanceof AdminRequestDTO adminRequestDTO){
                        return adminNewDataToAdmin(adminRequestDTO);
                    }
                }
            }
        }
        return null;
    }

    default AppUser appUserNewDataDTOToAppUser(AppUserNewDataDTO appUserNewDataDTO) {
        if (appUserNewDataDTO == null) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setFirstName(appUserNewDataDTO.getFirstName());
        appUser.setLastName(appUserNewDataDTO.getLastName());
        appUser.setPhoneNumber(appUserNewDataDTO.getPhoneNumber());
        appUser.setEmail(appUserNewDataDTO.getEmail());
        appUser.setPassword(appUserNewDataDTO.getPassword());
        if (appUserNewDataDTO.getUserInfoNewData() instanceof CustomerRequestDTO) {
            appUser.setRole(UserRole.CUSTOMER);
        } else {
            if (appUserNewDataDTO.getUserInfoNewData() instanceof ChefRequestDTO) {
                appUser.setRole(UserRole.CHEF);
            } else {
                if (appUserNewDataDTO.getUserInfoNewData() instanceof RiderRequestDTO) {
                    appUser.setRole(UserRole.RIDER);
                } else {
                    if (appUserNewDataDTO.getUserInfoNewData() instanceof AdminRequestDTO) {
                        appUser.setRole(UserRole.ADMIN);
                    }
                }
            }
        }
        appUser.setUserInfo(toUserInfo(appUserNewDataDTO.getUserInfoNewData()));
        return appUser;
    }

    Admin adminNewDataToAdmin(AdminRequestDTO adminRequestDTO);

    Customer customerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO);

    @Mapping(source = "restaurantId", target = "restaurant.restaurantId")
    Chef chefRequestDTOToChef(ChefRequestDTO chefRequestDTO);

    @Mapping(source = "longitude", target = "deliveryArea.longitude")
    @Mapping(source = "latitude", target = "deliveryArea.latitude")
    Rider riderRequestDTOToRider(RiderRequestDTO riderRequestDTO);
}