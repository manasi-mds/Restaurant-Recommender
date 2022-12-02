package com.dbproject.restaurantrecommender.dto.preference;

import lombok.Data;

@Data
public class RatingPreferenceDTO {
    Float minRating;
    Integer weight;
}
