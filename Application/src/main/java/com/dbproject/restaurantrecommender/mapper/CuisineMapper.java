package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.CuisineDTO;
import com.dbproject.restaurantrecommender.model.CuisineEntity;

public class CuisineMapper {
    public static CuisineDTO convert(CuisineEntity cuisineEntity) {
        CuisineDTO cuisineDTO = new CuisineDTO();
        cuisineDTO.setName(cuisineEntity.getName());
        cuisineDTO.setId(cuisineEntity.getId());
        return cuisineDTO;
    }
}
