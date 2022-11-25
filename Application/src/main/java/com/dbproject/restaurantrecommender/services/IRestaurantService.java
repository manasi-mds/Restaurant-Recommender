package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.controller.RestaurantController;
import com.dbproject.restaurantrecommender.dto.RestaurantDTO;

import java.util.List;

public interface IRestaurantService {
    List<RestaurantDTO> getAllRestaurants();
}
