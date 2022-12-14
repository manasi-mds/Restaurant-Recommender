package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.api.ResponseBody;
import com.dbproject.restaurantrecommender.api.ResponseGenerator;
import com.dbproject.restaurantrecommender.services.ICuisineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cuisine")
@RequiredArgsConstructor
public class CuisineController {

    private final ICuisineService cuisineService;

    @GetMapping
    ResponseBody getAllCuisines() {
        return ResponseGenerator.createSuccessResponse(cuisineService.getAllCuisines());
    }

}
