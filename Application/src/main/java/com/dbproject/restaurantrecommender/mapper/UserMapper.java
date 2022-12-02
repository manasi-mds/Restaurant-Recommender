package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.UserDTO;
import com.dbproject.restaurantrecommender.model.UserEntity;

public class UserMapper {
    public static UserEntity create(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        return userEntity;

    }

    public static UserDTO convert(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        return userDTO;
    }
}
