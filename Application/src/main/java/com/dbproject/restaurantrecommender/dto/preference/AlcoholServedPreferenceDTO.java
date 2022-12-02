package com.dbproject.restaurantrecommender.dto.preference;

import lombok.Data;

@Data
public class AlcoholServedPreferenceDTO {
    Boolean isAlcoholServed;
    Integer weight;
}
