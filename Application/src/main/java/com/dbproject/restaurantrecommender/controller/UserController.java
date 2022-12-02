package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.api.ResponseBody;
import com.dbproject.restaurantrecommender.api.ResponseGenerator;
import com.dbproject.restaurantrecommender.dto.UserDTO;
import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;
import com.dbproject.restaurantrecommender.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    ResponseBody createUser(@RequestBody UserDTO userDTO) {
        return ResponseGenerator.createSuccessResponse(userService.createUser(userDTO));
    }

    @GetMapping
    ResponseBody getAllUsers() {
        return ResponseGenerator.createSuccessResponse(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    ResponseBody getFollowedUsers(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(userService.getFollowedUsers(userId));
    }

    @PutMapping("/{userId}/follow/{followUserId}")
    ResponseBody followUser(@PathVariable Long userId, @PathVariable Long followUserId) {
        userService.followUser(userId, followUserId);
        return ResponseGenerator.createSuccessResponse("Followed the user "+ followUserId);
    }

    @PutMapping("/{userId}/likeRestaurant/{restaurantId}")
    ResponseBody likeRestaurant(@PathVariable Long userId, @PathVariable Long restaurantId) {
        userService.likeRestaurant(userId, restaurantId);
        return ResponseGenerator.createSuccessResponse("Liked the restaurant "+ restaurantId);
    }

    @GetMapping("/{userId}/likedRestaurants")
    ResponseBody getLikedRestaurants(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(userService.getLikedRestaurants(userId));
    }

    @GetMapping("/{userId}/potentalFriends")
    ResponseBody getPotentialFriends(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(userService.getPotentialFriends(userId));
    }

    @GetMapping("/{userId}/potentalRestaurants")
    ResponseBody getPotentialRestaurants(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(userService.getPotentialRestaurants(userId));
    }

    @PutMapping("/{userId}/createPreference")
    ResponseBody createPreference(@PathVariable Long userId, @RequestBody UserPreferenceDTO userPreferenceDTO) {
        userService.createPreference(userId, userPreferenceDTO);
        return ResponseGenerator.createSuccessResponse("Created preference for user "+ userId);
    }

}
