package com.dbproject.restaurantrecommender.dto;


import lombok.Data;

@Data
public class UserDTO {
    Long id;
    String name;
    String email;
    String password;
}
