package com.example.appusercomponent.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name="admins")
public class Admin extends UserInfo{
}
