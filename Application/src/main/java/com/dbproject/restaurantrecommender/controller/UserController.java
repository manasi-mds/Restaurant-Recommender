package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.api.ResponseBody;
import com.dbproject.restaurantrecommender.api.ResponseGenerator;
import com.dbproject.restaurantrecommender.dto.UserDTO;
import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;
import com.dbproject.restaurantrecommender.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.util.Preconditions;
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
    ResponseBody followUser(@PathVariable Long userId, @PathVariable Long followUserId, @RequestParam Boolean follow) {
        Preconditions.checkArgument(follow != null, "Follow parameter is required");
        userService.followUser(userId, followUserId, follow);
        String followedOrNot = follow ? "Followed" : "Unfollowed";
        return ResponseGenerator.createSuccessResponse(followedOrNot+ " the user "+ followUserId);
    }

    @PutMapping("/likeRestaurant/{userId}/{restaurantId}")
    ResponseBody likeRestaurant(@PathVariable Long userId, @PathVariable Long restaurantId, @RequestParam Boolean like) {
        Preconditions.checkArgument(like != null, "Query param like cannot be null");
        userService.likeRestaurant(userId, restaurantId, like);
        String likedOrNot = like ? "liked" : "unliked";
        return ResponseGenerator.createSuccessResponse("Successfully "+likedOrNot+" the restaurant "+ restaurantId);
    }

    @PutMapping("/dislikeRestaurant/{userId}/{restaurantId}")
    ResponseBody dislikeRestaurant(@PathVariable Long userId, @PathVariable Long restaurantId, @RequestParam Boolean dislike) {
        Preconditions.checkArgument(dislike != null, "Query param like cannot be null");
        userService.dislikeRestaurant(userId, restaurantId, dislike);
        String likedOrNot = dislike ? "disliked" : "un-disliked";
        return ResponseGenerator.createSuccessResponse("Successfully "+likedOrNot+" the restaurant "+ restaurantId);
    }

    @GetMapping("/{userId}/likedRestaurants")
    ResponseBody getLikedRestaurants(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(userService.getLikedRestaurants(userId));
    }

    @GetMapping("/{userId}/potentialFriends")
    ResponseBody getPotentialFriends(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(userService.getPotentialFriends(userId));
    }

    @GetMapping("/{userId}/potentialRestaurants")
    ResponseBody getPotentialRestaurants(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(userService.getPotentialRestaurants(userId));
    }

    @PutMapping("/{userId}/createPreference")
    ResponseBody createPreference(@PathVariable Long userId, @RequestBody UserPreferenceDTO userPreferenceDTO) {
        userService.createPreference(userId, userPreferenceDTO);
        return ResponseGenerator.createSuccessResponse("Created preference for user "+ userId);
    }

}
