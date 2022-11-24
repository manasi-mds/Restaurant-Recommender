package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.dto.CuisineDTO;
import com.dbproject.restaurantrecommender.services.ICuisineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cuisine")
@RequiredArgsConstructor
public class CuisineController {

    private final ICuisineService cuisineService;

    @GetMapping
    List<CuisineDTO> getAllCuisines() {
        return cuisineService.getAllCuisines();
    }

}
