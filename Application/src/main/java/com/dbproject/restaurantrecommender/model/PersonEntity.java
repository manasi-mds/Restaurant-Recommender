package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Person")
@Data
public class PersonEntity extends BaseEntity{
    String name;
    String email;
    String password;


    @Relationship(type = "CUISINE_PREFERENCES", direction = OUTGOING)
    Set<CuisinePreferences> cuisinePreference = new HashSet<>();

    @Relationship(type = "CREDIT_CARD_PREFERENCE", direction = OUTGOING)
    CreditCardEntity creditCardPreference;

    @Relationship(type = "ALCOHOL_PREFERENCE", direction = OUTGOING)
    AlcoholEntity alcoholPreference;

    @Relationship(type = "AMBIENCE_PREFERENCE", direction = OUTGOING)
    Set<AmbienceEntity> ambiencePreference = new HashSet<>();

    @Relationship(type = "WIFI_PREFERENCE", direction = OUTGOING)
    WifiEntity wifiPreference;

    @Relationship(type = "MINIMUM_RATING", direction = OUTGOING)
    StarsEntity hasRating;


    @Relationship(type = "FOLLOWING", direction = OUTGOING)
    Set<PersonEntity> following = new HashSet<>();

    @Relationship(type = "LIKE_RESTAURANT", direction = OUTGOING)
    Set<RestaurantEntity> likedRestaurants =  new HashSet<>();


}
