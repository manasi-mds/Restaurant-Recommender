package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("User")
@Data
public class UserEntity extends BaseEntity{
    String name;
    String email;
    String password;

    @Relationship(type = "CUISINE_PREFERENCE", direction = OUTGOING)
    Set<CuisinePreference> cuisinePreferences = new HashSet<>();

    @Relationship(type = "CREDIT_CARD_PREFERENCE", direction = OUTGOING)
    CreditCardPreference creditCardPreference;

    @Relationship(type = "ALCOHOL_PREFERENCE", direction = OUTGOING)
    AlcoholPreference alcoholPreference;

    @Relationship(type = "AMBIENCE_PREFERENCE", direction = OUTGOING)
    Set<AmbiencePreference> ambiencePreferences = new HashSet<>();

    @Relationship(type = "WIFI_PREFERENCE", direction = OUTGOING)
    WifiPreference wifiPreference;

    @Relationship(type = "MINIMUM_RATING", direction = OUTGOING)
    StarsPreference minimumRating;

    @Relationship(type = "FOLLOWING", direction = OUTGOING)
    Set<UserEntity> following = new HashSet<>();

    @Relationship(type = "LIKE_RESTAURANT", direction = OUTGOING)
    Set<RestaurantEntity> likedRestaurants =  new HashSet<>();

    public void followUser(UserEntity user2) {
        if(following==null)
            following = new HashSet<>();
        Preconditions.checkArgument(following.contains(user2), "User is already following " + user2.getName());
        following.add(user2);
    }

    public void likeRestaurant(RestaurantEntity restaurant) {
        if(likedRestaurants==null)
            likedRestaurants = new HashSet<>();
        Preconditions.checkArgument(likedRestaurants.contains(restaurant), "User has already liked " + restaurant.getName());
        likedRestaurants.add(restaurant);
    }
}
