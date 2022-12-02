package com.dbproject.restaurantrecommender.dto.preference;

import lombok.Data;

@Data
public class WifiPreferenceDTO {
    String wifiType;
    Integer weight;
}
