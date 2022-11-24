package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.CuisineDTO;

import java.util.List;

public interface ICuisineService {
    List<CuisineDTO> getAllCuisines();
}
