package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.dto.CuisineDTO;
import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.services.IRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final IRestaurantService restaurantService;

    @GetMapping
    ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(),  HttpStatus.OK);
    }
}
