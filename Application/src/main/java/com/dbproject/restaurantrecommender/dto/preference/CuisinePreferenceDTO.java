package com.dbproject.restaurantrecommender.dto.preference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CuisinePreferenceDTO {
    Long cuisineId;
    String cuisineName;
    Integer weight;
}
