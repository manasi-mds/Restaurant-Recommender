package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.api.ResponseBody;
import com.dbproject.restaurantrecommender.api.ResponseGenerator;
import com.dbproject.restaurantrecommender.services.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.web.bind.annotation.*;

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
    ResponseBody getPreferredRestaurants(@PathVariable Long userId, @RequestParam(required = false) Double lat, @RequestParam(required = false) Double lon) {
        return ResponseGenerator.createSuccessResponse(restaurantService.getPreferredRestaurants(userId, lat, lon));
    }
}
