package com.dbproject.restaurantrecommender.dto;

import com.dbproject.restaurantrecommender.dto.preference.*;
import com.dbproject.restaurantrecommender.dto.preference.WifiPreferenceDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPreferenceDTO {
    Long userId;
    String name;
    String email;
    String password;
    AlcoholServedPreferenceDTO alcoholServed;
    List<CuisinePreferenceDTO> cuisines;
    List<AmbiencePreferenceDTO> ambiences;
    CreditCardPreferenceDTO creditCardAccepted;
    WifiPreferenceDTO wifiTypeAvailable;
    RatingPreferenceDTO minimumRating;
    OutdoorSeatingPreferenceDTO outdoorSeating;
    Double distance;
}
