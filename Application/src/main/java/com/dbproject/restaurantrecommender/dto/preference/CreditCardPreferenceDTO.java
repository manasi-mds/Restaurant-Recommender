package com.dbproject.restaurantrecommender.dto.preference;

import lombok.Data;

@Data
public class CreditCardPreferenceDTO {
    Boolean isCreditCardAccepted;
    Integer weight;
}
