package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;
import com.dbproject.restaurantrecommender.dto.preference.AlcoholServedPreferenceDTO;
import com.dbproject.restaurantrecommender.dto.preference.CuisinePreferenceDTO;
import com.dbproject.restaurantrecommender.model.UserEntity;


public class UserPreferenceMapper {
    public static UserEntity create(UserPreferenceDTO userPreferenceDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userPreferenceDTO.getName());
        userEntity.setEmail(userPreferenceDTO.getEmail());
        userEntity.setPassword(userPreferenceDTO.getPassword());
        return userEntity;

    }
    public static UserPreferenceDTO convert(UserEntity userEntity) {
        UserPreferenceDTO userDTO = new UserPreferenceDTO();
        userDTO.setUserId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());

        AlcoholServedPreferenceDTO alcoholServedPreferenceDTO = new AlcoholServedPreferenceDTO();
        if(userEntity.getAlcoholPreference()!=null) alcoholServedPreferenceDTO.setIsAlcoholServed(userEntity.getAlcoholPreference().getAlcoholEntity().isAlcoholServed());
//        userDTO.setCuisines(userEntity.getCuisinePreferences().stream().map(cuisinePreferenceEntity -> {
////            return new CuisinePreferenceDTO(cuisinePreferenceEntity.getCuisineEntity());
//        }).toList());


        return userDTO;
    }
}
