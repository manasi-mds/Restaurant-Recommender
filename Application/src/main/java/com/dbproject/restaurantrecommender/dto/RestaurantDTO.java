package com.dbproject.restaurantrecommender.dto;

import com.dbproject.restaurantrecommender.enums.WifiType;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantDTO {
    Long id;
    String name;
    String address;
    String businessId;
    Double latitude;
    Double longitude;
    Integer reviewCount;
    String hoursMon;
    String hoursTue;
    String hoursWed;
    String hoursThu;
    String hoursFri;
    String hoursSat;
    String hoursSun;
    List<CuisineDTO> cuisines;
    List<AmbienceDTO> ambiences;
    Boolean isAlcoholServed;
    WifiType wifi;
    Double rating;
    Boolean isCreditCardAccepted;
    Boolean isOpen;
    Boolean isOutdoorSeatingAvailable;
}
