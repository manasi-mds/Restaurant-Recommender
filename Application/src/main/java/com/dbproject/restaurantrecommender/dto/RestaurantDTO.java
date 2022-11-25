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
    Float latitude;
    Float longitude;
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
    Float rating;
    Boolean isCreditCardAccepted;
    Boolean isOpen;
    Boolean isOutdoorSeatingAvailable;
}
