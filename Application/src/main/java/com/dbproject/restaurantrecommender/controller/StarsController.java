//package com.dbproject.restaurantrecommender.controller;
//
//
//import com.dbproject.restaurantrecommender.api.ResponseBody;
//import com.dbproject.restaurantrecommender.api.ResponseGenerator;
//import com.dbproject.restaurantrecommender.services.IStarsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/stars")
//@RequiredArgsConstructor
//public class StarsController {
//    private final IStarsService starsService;
//
//    @GetMapping
//    public ResponseBody getAllStars() {
//        return ResponseGenerator.createSuccessResponse(starsService.getAllStars());
//    }
//}
