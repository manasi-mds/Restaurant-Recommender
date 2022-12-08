package com.dbproject.restaurantrecommender.dto;

import com.dbproject.restaurantrecommender.enums.WifiType;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RestaurantUserDTO {
    Long id;
    String name;
    String address;
    String businessId;
    Double latitude;
    Double longitude;
    Integer reviewCount;
    Map<String, String> hours = new HashMap<>();
    List<CuisineDTO> cuisines;
    List<AmbienceDTO> ambiences;
    Boolean isAlcoholServed;
    WifiType wifi;
    Double rating;
    Boolean isCreditCardAccepted;
    Boolean isOpen;
    Boolean isOutdoorSeatingAvailable;
    Integer likeDislike;
    Double cosineSimilarity;
    Double distance;
}
