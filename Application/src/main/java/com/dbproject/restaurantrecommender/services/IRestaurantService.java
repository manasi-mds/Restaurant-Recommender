package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.RestaurantUserDTO;

import java.util.List;
import java.util.Optional;

public interface IRestaurantService {
    List<RestaurantUserDTO> getAllRestaurants(Long userId);
    Optional<RestaurantDTO> getRestaurantById(Long restaurantId);
    List<RestaurantUserDTO> getPreferredRestaurants(Long userId);
}
