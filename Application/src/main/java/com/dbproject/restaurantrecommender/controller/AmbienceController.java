package com.dbproject.restaurantrecommender.controller;

import com.dbproject.restaurantrecommender.api.ResponseBody;
import com.dbproject.restaurantrecommender.api.ResponseGenerator;
import com.dbproject.restaurantrecommender.services.IAmbienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ambience")
@RequiredArgsConstructor
public class AmbienceController {

    private final IAmbienceService ambienceService;

    @GetMapping
    public ResponseBody getAllAmbience() {
        return ResponseGenerator.createSuccessResponse(ambienceService.getAllAmbience());
    }
}

