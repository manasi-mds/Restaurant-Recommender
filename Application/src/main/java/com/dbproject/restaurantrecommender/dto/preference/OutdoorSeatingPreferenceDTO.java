package com.dbproject.restaurantrecommender.dto.preference;

import lombok.Data;

@Data
public class OutdoorSeatingPreferenceDTO {
    Boolean isOutdoorSeatingAvailable;
    Integer weight;
}
