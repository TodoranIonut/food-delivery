package com.learning.proj.food.controller.mappers;

import com.learning.proj.food.controller.dto.user.AdminDTO;
import com.learning.proj.food.controller.dto.user.AppUserDTO;
import com.learning.proj.food.controller.dto.user.ChefDTO;
import com.learning.proj.food.controller.dto.user.CustomerDTO;
import com.learning.proj.food.controller.dto.user.RiderDTO;
import com.learning.proj.food.controller.dto.user.UserInfoDTO;
import com.learning.proj.food.domain.entity.Admin;
import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.Customer;
import com.learning.proj.food.domain.entity.Rider;
import com.learning.proj.food.domain.entity.UserInfo;
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    default UserInfoDTO toUserInfoDTO(UserInfo userInfo) {

        userInfo = (UserInfo) Hibernate.unproxy(userInfo);

        if (userInfo instanceof Customer customer) {
            return customerToCustomerDTO(customer);
        } else {
            if (userInfo instanceof Chef chef) {
                return chefToChefDTO(chef);
            } else {
                if (userInfo instanceof Rider rider) {
                    return riderToRiderDTO(rider);
                } else {
                    if (userInfo instanceof Admin admin) {
                        return adminToAdminDTO(admin);
                    }
                }
            }
        }
        return null;
    }

    default UserInfo toUserInfo(UserInfoDTO userInfoDTO) {

        if (userInfoDTO instanceof CustomerDTO customerDTO) {
            return customerDTOToCustomer(customerDTO);
        } else {
            if (userInfoDTO instanceof ChefDTO chefDTO) {
                return chefDTOToChef(chefDTO);
            } else {
                if (userInfoDTO instanceof RiderDTO riderDTO) {
                    return riderDTOToRider(riderDTO);
                } else {
                    if (userInfoDTO instanceof AdminDTO adminDTO) {
                        return adminDTOToAdmin(adminDTO);
                    }
                }
            }
        }
        return null;
    }

    @Mapping(source = "id", target = "appUserId")
    AppUserDTO appUserToAppUserDTO(AppUser appUser);

    @Mapping(source = "appUserId", target = "id")
    AppUser appUserDTOToAppUser(AppUserDTO appUserDTO);

    @Mapping(source = "userInfoId", target = "id")
    Admin adminDTOToAdmin(AdminDTO adminDTO);

    @Mapping(source = "id", target = "userInfoId")
    AdminDTO adminToAdminDTO(Admin admin);

    @Mapping(source = "userInfoId", target = "id")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    @Mapping(source = "id", target = "userInfoId")
    CustomerDTO customerToCustomerDTO(Customer customer);

    @Mapping(source = "userInfoId", target = "id")
    @Mapping(source = "restaurantId", target = "restaurant.restaurantId")
    Chef chefDTOToChef(ChefDTO chefDTO);

    @Mapping(source = "id", target = "userInfoId")
    @Mapping(source = "restaurant.restaurantId", target = "restaurantId")
    ChefDTO chefToChefDTO(Chef chef);

    @Mapping(source = "userInfoId", target = "id")
    @Mapping(source = "longitude", target = "deliveryArea.longitude")
    @Mapping(source = "latitude", target = "deliveryArea.latitude")
    Rider riderDTOToRider(RiderDTO riderDTO);

    @Mapping(source = "id", target = "userInfoId")
    @Mapping(source = "deliveryArea.longitude", target = "longitude")
    @Mapping(source = "deliveryArea.latitude", target = "latitude")
    RiderDTO riderToRiderDTO(Rider rider);

    List<ChefDTO> chefSetToChefResponseDTOList(Set<Chef> chefs);

    Set<RiderDTO> riderSetToRiderResponseDTOSet(Set<Rider> riders);

    default List<AppUserDTO> chefSetToAppUserDTO(Set<Chef> chefs) {
        if (chefs == null) {
            return null;
        }

        List<AppUserDTO> appUserDTOList = new ArrayList<>();
        for (Chef chef : chefs) {

            AppUserDTO appUserDTO = appUserToAppUserDTO(chef.getAppUser());
            appUserDTO.setUserInfo(toUserInfoDTO(chef));

            appUserDTOList.add(appUserDTO);
        }
        return appUserDTOList;
    }

    default Set<AppUserDTO> riderSetToAppUserDTOSet(Set<Rider> riders) {
        if (riders == null) {
            return null;
        }

        Set<AppUserDTO> appUserDTOList = new HashSet<>();
        for (Rider rider : riders) {

            AppUserDTO appUserDTO = appUserToAppUserDTO(rider.getAppUser());
            appUserDTO.setUserInfo(toUserInfoDTO(rider));

            appUserDTOList.add(appUserDTO);
        }
        return appUserDTOList;
    }
}