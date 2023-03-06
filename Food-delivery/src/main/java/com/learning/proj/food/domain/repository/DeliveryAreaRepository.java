package com.learning.proj.food.domain.repository;

import com.learning.proj.food.domain.entity.DeliveryArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAreaRepository extends JpaRepository<DeliveryArea, String> {
}
