package com.example.appusercomponent.domain.repository;

import com.example.appusercomponent.domain.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChefRepository extends JpaRepository<Chef,String> {

    Set<Chef> findAllByRestaurantId(String restaurantId);
}
