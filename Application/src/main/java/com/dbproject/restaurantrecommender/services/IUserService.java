package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.UserDTO;

import java.util.List;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    void followUser(Long userId, Long followUserId);
    List<UserDTO> getFollowedUsers(Long userId);
    void likeRestaurant(Long userId, Long restaurantId);
}
