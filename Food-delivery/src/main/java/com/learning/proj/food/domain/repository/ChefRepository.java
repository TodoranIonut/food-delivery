package com.learning.proj.food.domain.repository;

import com.learning.proj.food.domain.entity.Chef;
import com.learning.proj.food.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChefRepository extends JpaRepository<Chef, String> {

    Set<Chef> findAllByRestaurant(Restaurant restaurant);
}
