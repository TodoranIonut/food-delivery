package com.learning.proj.food.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "riders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rider extends UserInfo {

    private String vehicle;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="delivery_area_id")
    private DeliveryArea deliveryArea;
}
