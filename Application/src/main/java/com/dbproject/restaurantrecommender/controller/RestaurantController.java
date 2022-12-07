package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.api.ResponseBody;
import com.dbproject.restaurantrecommender.api.ResponseGenerator;
import com.dbproject.restaurantrecommender.services.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantService restaurantService;

    @GetMapping("/{userId}")
    ResponseBody getAllRestaurants(@PathVariable Long userId) {
        Preconditions.checkArgument(userId != null, "User id cannot be null");
        return ResponseGenerator.createSuccessResponse(restaurantService.getAllRestaurants(userId));
    }

    @GetMapping("/getPreferredRestaurants/{userId}")
    ResponseBody getPreferredRestaurants(@PathVariable Long userId) {
        return ResponseGenerator.createSuccessResponse(restaurantService.getPreferredRestaurants(userId));
    }
}
