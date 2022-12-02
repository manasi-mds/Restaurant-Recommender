package com.dbproject.restaurantrecommender.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyNameException;
import org.springframework.data.neo4j.core.schema.Property;


@Data
class AlcoholServed {
    Boolean isAlcoholServed;
    Float weight;
}

@Data
class Cuisine {
    Long cuisineId;
    Float weight;
}

@Data
class Ambience {
    Long ambienceId;
    Float weight;
}

@Data
class CreditCardAccepted {
    Boolean isCreditCardAccepted;
    Float weight;
}

@Data
class WifiTypeAvailable {
    String wifiType;
    Float weight;
}




@Data
// TODO: Add json ignore properties
public class UserPreferenceDTO {
    String name;

}
