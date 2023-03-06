package com.learning.proj.food.domain.repository;

import com.learning.proj.food.domain.entity.DeliveryArea;
import com.learning.proj.food.domain.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RiderRepository extends JpaRepository<Rider, String> {

    @Query(
            value = "SELECT * FROM RIDERS " +
                    "INNER JOIN DELIVERY_AREA " +
                    "ON RIDERS.DELIVERY_AREA_ID = DELIVERY_AREA.AREA_ID " +
                    "WHERE ACOS(SIN(DELIVERY_AREA.LATITUDE*PI()/180)*SIN(:#{#deliveryArea.latitude}*PI()/180) + " +
                    "COS(DELIVERY_AREA.LATITUDE*PI()/180)*COS(:#{#deliveryArea.latitude}*PI()/180) * " +
                    "COS(:#{#deliveryArea.longitude}*PI()/180 - " +
                    "DELIVERY_AREA.LONGITUDE*PI()/180) ) * 6371000 < :#{#deliveryArea.radius}",
            nativeQuery = true
    )
    Set<Rider> findAllByDeliveryArea(DeliveryArea deliveryArea);
}
