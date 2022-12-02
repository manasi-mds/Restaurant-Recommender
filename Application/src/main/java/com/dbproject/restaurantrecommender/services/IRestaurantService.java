package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;

import java.util.List;
import java.util.Optional;

public interface IRestaurantService {
    List<RestaurantDTO> getAllRestaurants();
    Optional<RestaurantDTO> getRestaurantById(Long restaurantId);
}
