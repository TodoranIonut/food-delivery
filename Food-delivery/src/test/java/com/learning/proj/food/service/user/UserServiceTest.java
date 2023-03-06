package com.learning.proj.food.service.user;

import com.learning.proj.food.controller.dto.user.AppUserNewDataDTO;
import com.learning.proj.food.controller.dto.user.CustomerRequestDTO;
import com.learning.proj.food.controller.mappers.AppUserMapper;
import com.learning.proj.food.controller.mappers.AppUserNewDataMapper;
import com.learning.proj.food.domain.entity.AppUser;
import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.Customer;
import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Restaurant;
import com.learning.proj.food.domain.entity.Rider;
import com.learning.proj.food.domain.entity.UserRole;
import com.learning.proj.food.domain.repository.AppUserRepository;
import com.learning.proj.food.domain.repository.ChefRepository;
import com.learning.proj.food.domain.repository.RestaurantRepository;
import com.learning.proj.food.domain.repository.RiderRepository;
import com.learning.proj.food.exception.appUser.AppUserEmailConflictException;
import com.learning.proj.food.exception.appUser.AppUserEmailNotFoundException;
import com.learning.proj.food.exception.appUser.AppUserIdNotFoundException;
import com.learning.proj.food.exception.appUser.AppUserPhoneNumberConflictException;
import com.learning.proj.food.exception.chef.ChefsMissingFromRestaurantException;
import com.learning.proj.food.exception.customer.CustomerMissingParameterException;
import com.learning.proj.food.exception.customer.CustomerPhoneNumberConflictException;
import com.learning.proj.food.exception.deliveryArea.DeliveryAreaCoordinatesOutOfRangeException;
import com.learning.proj.food.exception.restaurant.RestaurantIdNotFoundException;
import com.learning.proj.food.exception.rider.RidersMissingFromAreaException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ChefRepository chefRepository;
    @Mock
    private RiderRepository riderRepository;
    @InjectMocks
    private UserServiceImpl underTest;

    @Mock
    private PasswordEncoder passwordEncoder;

    AppUserMapper appUserMapper;// = Mappers.getMapper(AppUserMapper.class);
    AppUserNewDataMapper appUserNewDataMapper;// = Mappers.getMapper(AppUserMapper.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @SneakyThrows
    @Test
    void findAppUserById() {
        //given
        String customerId = "idTest";

        //when
        when(appUserRepository.findById(any())).thenReturn(Optional.of(new AppUser()));
        underTest.findAppUserById(customerId);

        //then
        verify(appUserRepository).findById(customerId);
    }

    @Test
    void customerNotFoundById() {
        //given
        String appUserId = "idTest";

        //when
        when(appUserRepository.findById(any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> underTest.findAppUserById(appUserId))
                .isInstanceOf(AppUserIdNotFoundException.class)
                .hasMessageContaining(String.format("app user id %s is NOT found", appUserId));
    }

    @SneakyThrows
    @Test
    void registerNewCustomer() {
        //given

        AppUserNewDataDTO appUserNewDataDTO = new AppUserNewDataDTO();
        appUserNewDataDTO.setFirstName("firstNameTest");
        appUserNewDataDTO.setLastName("lastNameTest");
        appUserNewDataDTO.setEmail("emailTest");
        appUserNewDataDTO.setPassword("passwordTest");
        appUserNewDataDTO.setPhoneNumber("0000000000");
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setStreetAddress("addressTest");
        appUserNewDataDTO.setUserInfoNewData(customerRequestDTO);
        AppUser appUser = appUserNewDataMapper.appUserNewDataDTOToAppUser(appUserNewDataDTO);

        //when
        underTest.registerNewAppUser(appUser);

        //then
        ArgumentCaptor<AppUser> customerCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(customerCaptor.capture());
        AppUser capturedAppUser = customerCaptor.getValue();
        assertThat(appUser).isEqualTo(capturedAppUser);
    }

    @Test
    void registerNewCustomerWithIncompleteData() {
        //given
        Customer givenCustomer = new Customer();
        AppUser appUser = new AppUser();
        appUser.setUserInfo(givenCustomer);

        //then
        assertThatThrownBy(() -> underTest.registerNewAppUser(appUser))
                .isInstanceOf(CustomerMissingParameterException.class)
                .hasMessageContaining("missing customer entity parameters firstName, lastName, email, password, phoneNumber, streetAddress");
    }

    @SneakyThrows
    @Test
    void updateCustomer() {
        //given
        Customer givenCustomer = new Customer();
        AppUser appUser = new AppUser();
        appUser.setFirstName("firstNameTest");
        appUser.setLastName("lastNameTest");
        appUser.setEmail("emailTest");
//        appUser.setPassword(passwordEncoder.encode("passwordTest"));
        appUser.setPassword("passwordTest");
        appUser.setPhoneNumber("0000000000");
        appUser.setRole(UserRole.CUSTOMER);
        givenCustomer.setStreetAddress("addressTest");
        appUser.setUserInfo(givenCustomer);

        //when
        when(appUserRepository.findById(any())).thenReturn(Optional.of(appUser));
        underTest.updateAppUser(null, appUser);

        //then
        ArgumentCaptor<AppUser> appUserCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(appUserCaptor.capture());
        AppUser capturedAppUser = appUserCaptor.getValue();
        assertThat(appUser).isEqualTo(capturedAppUser);
    }

    @SneakyThrows
    @Test
    void updateAppUserWithInvalidData() {
        //given
        Customer givenCustomer = new Customer();
        AppUser appUser = new AppUser();
        appUser.setUserInfo(givenCustomer);

        //then
        assertThatThrownBy(() -> underTest.updateAppUser(null, appUser))
                .isInstanceOf(CustomerMissingParameterException.class)
                .hasMessageContaining("missing customer entity parameters firstName, lastName, email, password, phoneNumber, streetAddress");
    }

    @SneakyThrows
    @Test
    void findAppUserByEmail() {
        //given
        String customerEmail = "emailTest";

        //when
        when(appUserRepository.findByEmail(any())).thenReturn(Optional.of(new AppUser()));
        underTest.findAppUserByEmail(customerEmail);

        //then
        verify(appUserRepository).findByEmail(customerEmail);
    }

    @Test
    void customerNotFoundByEmail() {
        //given
        String customerEmail = "emailTest";

        //when
        when(appUserRepository.findByEmail(any())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> underTest.findAppUserByEmail(customerEmail))
                .isInstanceOf(AppUserEmailNotFoundException.class)
                .hasMessageContaining(String.format("app user email %s is NOT found", customerEmail));
    }

    @SneakyThrows
    @Test
    void deleteAppUserById() {
        //given
        Customer givenCustomer = new Customer();
        AppUser appUser = new AppUser();
        appUser.setUserInfo(givenCustomer);
        appUser.setUserInfo(givenCustomer);
        String customerId = "idTest";

        // when
        when(appUserRepository.findById(any())).thenReturn(Optional.of(appUser));
        underTest.deleteAppUserById(customerId);
        //then
        verify(appUserRepository).delete(appUser);
    }

    @SneakyThrows
    @Test
    void deleteAppUserByInvalidId() {
        //given
        String customerId = "idTest";

        //when
        underTest.deleteAppUserById(customerId);
    }

    @Test
    void checkAppUserPhoneNumberIsAvailable() throws CustomerPhoneNumberConflictException {
        //given
        String phoneNumberTest = "101010101010";

        //when
        when(appUserRepository.existsAppUserByPhoneNumberAndRole(phoneNumberTest,UserRole.CUSTOMER)).thenReturn(true);

        //then
        assertThatThrownBy(() -> underTest.checkAppUserPhoneNumberIsAvailable(phoneNumberTest, UserRole.CUSTOMER))
                .isInstanceOf(AppUserPhoneNumberConflictException.class)
                .hasMessageContaining(String.format("app user phone number %s is unavailable", phoneNumberTest));
    }

    @Test
    void checkAppUserEmailIsAvailable() {
        //given
        String emailTest = "emailTest";

        //when
        when(appUserRepository.existsAppUserByEmailAndRole(emailTest, UserRole.CUSTOMER)).thenReturn(true);

        //then
        assertThatThrownBy(() -> underTest.checkAppUserEmailIsAvailable(emailTest,UserRole.CUSTOMER))
                .isInstanceOf(AppUserEmailConflictException.class)
                .hasMessageContaining(String.format("app user email %s is unavailable", emailTest));
    }

    @SneakyThrows
    @Test
    void findChefsByRestaurantId() {
        //given
        Chef chef1 = new Chef();
        Chef chef2 = new Chef();
        chef1.setAppUser(new AppUser());
        chef2.setAppUser(new AppUser());

        Set<Chef> chefs = new HashSet<>(List.of(chef1,chef2));
        String restaurantId = "restaurantId";
        Restaurant givenRestaurant = new Restaurant();

        //when
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(givenRestaurant));
        when(chefRepository.findAllByRestaurant(any())).thenReturn(chefs);

        //then
        ArgumentCaptor<Restaurant> restaurantArgumentCaptor = ArgumentCaptor.forClass(Restaurant.class);
        underTest.findChefsByRestaurantId(restaurantId);

        verify(chefRepository).findAllByRestaurant(restaurantArgumentCaptor.capture());

        Restaurant capturedRestaurant = restaurantArgumentCaptor.getValue();
        assertThat(givenRestaurant).isEqualTo(capturedRestaurant);
    }

    @SneakyThrows
    @Test
    void findChefsByInvalidRestaurantId() {
        //given
        String restaurantId = "restaurantId";

        //then
        assertThatThrownBy(() -> underTest.findChefsByRestaurantId(restaurantId))
                .isInstanceOf(RestaurantIdNotFoundException.class)
                .hasMessageContaining(String.format("restaurant id %s not found", restaurantId));
    }

    @Test
    void noChefsFoundAtRestaurant() {
        //given
        String restaurantId = "restaurantId";
        Restaurant givenRestaurant = new Restaurant();

        //when
        when(restaurantRepository.findById(any())).thenReturn(Optional.of(givenRestaurant));
        when(chefRepository.findAllByRestaurant(any())).thenReturn(new HashSet<>());

        //then
        assertThatThrownBy(() -> underTest.findChefsByRestaurantId(restaurantId))
                .isInstanceOf(ChefsMissingFromRestaurantException.class)
                .hasMessageContaining(String.format("restaurant id %s have no chef", givenRestaurant.getRestaurantId()));
    }

    @SneakyThrows
    @Test
    void findRidersByDeliveryArea() {
        //given
        DeliveryArea givenDeliveryArea = new DeliveryArea(null, 10.0, 10.0, 15000, null);
        Rider rider1 = new Rider();
        Rider rider2 = new Rider();
        AppUser appUser1 = new AppUser();
        AppUser appUser2 = new AppUser();
        rider1.setDeliveryArea(new DeliveryArea(null, 10.0, 10.1, null, null));
        appUser1.setFirstName("rider1");
        rider2.setDeliveryArea(new DeliveryArea(null, 10.0, 10.2, null, null));
        appUser2.setFirstName("rider2");
        rider1.setAppUser(appUser1);
        rider2.setAppUser(appUser2);
        List<Rider> givenRiders = Arrays.asList(rider1, rider2);

        //when
        when(riderRepository.findAll()).thenReturn(givenRiders);

        //then
        Set<Rider> findRiders = underTest.findRidersByDeliveryArea(givenDeliveryArea);
        assertTrue(findRiders.contains(rider1));
    }

    @SneakyThrows
    @Test
    void findRidersByDeliveryAreaQuery() {
        //given
        DeliveryArea givenDeliveryArea = new DeliveryArea(null, 10.0, 10.0, 100, null);

        //when
        when(riderRepository.findAllByDeliveryArea(any())).thenReturn(new HashSet<>(List.of(new Rider())));
        underTest.findRidersByDeliveryAreaQuery(givenDeliveryArea);

        //then
        verify(riderRepository).findAllByDeliveryArea(givenDeliveryArea);
    }

    @Test
    void findRidersByDeliveryAreaOutOfRange() {
        //given
        DeliveryArea latitudeOver = new DeliveryArea(null, 91.0, 10.0, 2000, new Restaurant());
        DeliveryArea latitudeUnder = new DeliveryArea(null, -91.0, 10.0, 2000, new Restaurant());
        DeliveryArea longitudeOver = new DeliveryArea(null, 10.0, 181.0, 2000, new Restaurant());
        DeliveryArea longitudeUnder = new DeliveryArea(null, 10.0, -181.0, 2000, new Restaurant());
        DeliveryArea radiusOver = new DeliveryArea(null, 10.0, 10.0, 40000, new Restaurant());
        DeliveryArea radiusUnder = new DeliveryArea(null, 10.0, 10.0, -5, new Restaurant());
        DeliveryArea allValuesOverRange = new DeliveryArea(null, 91.0, 181.0, 30001, new Restaurant());
        DeliveryArea allValuesUnderRange = new DeliveryArea(null, -91.0, -181.0, -1, new Restaurant());

        //then
        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(radiusOver))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range is too big");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(radiusUnder))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range cannot be negative");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(latitudeOver))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("latitude cannot exceed 90 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(latitudeUnder))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("latitude cannot be less then -90 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(longitudeOver))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("longitude cannot exceed 180 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(longitudeUnder))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("longitude cannot be less then -180 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(allValuesOverRange))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range is too big and latitude cannot exceed 90 degrees and longitude cannot exceed 180 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(allValuesUnderRange))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range cannot be negative and latitude cannot be less then -90 degrees and longitude cannot be less then -180 degrees");
    }

    @Test
    void findRidersByDeliveryAreaOutOfRangeQuery() {
        //given
        DeliveryArea latitudeOver = new DeliveryArea(null, 91.0, 10.0, 2000, new Restaurant());
        DeliveryArea latitudeUnder = new DeliveryArea(null, -91.0, 10.0, 2000, new Restaurant());
        DeliveryArea longitudeOver = new DeliveryArea(null, 10.0, 181.0, 2000, new Restaurant());
        DeliveryArea longitudeUnder = new DeliveryArea(null, 10.0, -181.0, 2000, new Restaurant());
        DeliveryArea radiusOver = new DeliveryArea(null, 10.0, 10.0, 40000, new Restaurant());
        DeliveryArea radiusUnder = new DeliveryArea(null, 10.0, 10.0, -5, new Restaurant());
        DeliveryArea allValuesOverRange = new DeliveryArea(null, 91.0, 181.0, 30001, new Restaurant());
        DeliveryArea allValuesUnderRange = new DeliveryArea(null, -91.0, -181.0, -1, new Restaurant());

        //then
        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(radiusOver))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range is too big");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(radiusUnder))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range cannot be negative");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(latitudeOver))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("latitude cannot exceed 90 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(latitudeUnder))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("latitude cannot be less then -90 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(longitudeOver))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("longitude cannot exceed 180 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(longitudeUnder))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("longitude cannot be less then -180 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(allValuesOverRange))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range is too big and latitude cannot exceed 90 degrees and longitude cannot exceed 180 degrees");

        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(allValuesUnderRange))
                .isInstanceOf(DeliveryAreaCoordinatesOutOfRangeException.class)
                .hasMessageContaining("range cannot be negative and latitude cannot be less then -90 degrees and longitude cannot be less then -180 degrees");
    }

    @Test
    void noRidersInDeliveryArea() {
        //given
        DeliveryArea givenDeliveryArea = new DeliveryArea(null, 10.0, 10.0, 2000, null);

        //when
        when(riderRepository.findAll()).thenReturn(new ArrayList<>());

        //then
        assertThatThrownBy(() -> underTest.findRidersByDeliveryArea(givenDeliveryArea))
                .isInstanceOf(RidersMissingFromAreaException.class)
                .hasMessageContaining(String.format("no riders found in delivery area %s", givenDeliveryArea));
    }

    @Test
    void noRidersInDeliveryAreaQuery() {
        //given
        DeliveryArea givenDeliveryArea = new DeliveryArea(null, 10.0, 10.0, 2000, null);

        //when
        when(riderRepository.findAllByDeliveryArea(any())).thenReturn(new HashSet<>());

        //then
        assertThatThrownBy(() -> underTest.findRidersByDeliveryAreaQuery(givenDeliveryArea))
                .isInstanceOf(RidersMissingFromAreaException.class)
                .hasMessageContaining(String.format("no riders found in delivery area %s", givenDeliveryArea));
    }
}