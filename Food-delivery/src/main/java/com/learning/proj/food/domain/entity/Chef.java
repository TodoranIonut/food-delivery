package com.learning.proj.food.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chefs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chef extends UserInfo {

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
}
