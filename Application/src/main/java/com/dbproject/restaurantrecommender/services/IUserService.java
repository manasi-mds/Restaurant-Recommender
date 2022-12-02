package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.UserDTO;
import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;

import java.util.List;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    void followUser(Long userId, Long followUserId);
    List<UserDTO> getFollowedUsers(Long userId);
    void likeRestaurant(Long userId, Long restaurantId);
    List<RestaurantDTO> getLikedRestaurants(Long userId);
    List<UserDTO> getPotentialFriends(Long userId);
    List<RestaurantDTO> getPotentialRestaurants(Long userId);

    void createPreference(Long userId, UserPreferenceDTO userPreferenceDTO);
}
