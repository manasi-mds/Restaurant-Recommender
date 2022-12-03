package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("User")
@Getter
@Setter
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
    RatingPreference minimumRating;

    @Relationship(type = "OUTDOOR_SEATING_PREFERENCE", direction = OUTGOING)
    OutdoorSeatingPreference outdoorSeatingPreference;

    @Relationship(type = "FOLLOWING", direction = OUTGOING)
    Set<UserEntity> following = new HashSet<>();

    @Relationship(type = "LIKE_RESTAURANT", direction = OUTGOING)
    Set<RestaurantEntity> likedRestaurants =  new HashSet<>();

    public void followUser(UserEntity user2) {
        if(this.following==null)
            this.following = new HashSet<>();

        Preconditions.checkArgument(!this.following.contains(user2), "User is already following " + user2.getName());
        this.following.add(user2);
    }

    public void likeRestaurant(RestaurantEntity restaurant) {
        if(this.likedRestaurants==null)
            this.likedRestaurants = new HashSet<>();

        Preconditions.checkArgument(!this.likedRestaurants.contains(restaurant), "User has already liked " + restaurant.getName());
        this.likedRestaurants.add(restaurant);
    }

    public void addCuisinePreferences(Map<CuisineEntity, Integer> cuisinePreferencesMap) {
        if(this.cuisinePreferences==null)
            this.cuisinePreferences = new HashSet<>();
        else
            this.cuisinePreferences.clear();

        cuisinePreferencesMap.forEach((cuisineEntity, weight) -> {
            CuisinePreference cuisinePreference = new CuisinePreference();
            cuisinePreference.setWeight(weight);
            cuisinePreference.setCuisineEntity(cuisineEntity);
            this.cuisinePreferences.add(cuisinePreference);
        });
    }

    public void addAmbiencePreferences(Map<AmbienceEntity, Integer> preferredAmbienceWeight) {
        if(this.ambiencePreferences==null)
            this.ambiencePreferences = new HashSet<>();
        else
            this.ambiencePreferences.clear();

        preferredAmbienceWeight.forEach((ambienceEntity, weight) -> {
            AmbiencePreference ambiencePreference = new AmbiencePreference();
            ambiencePreference.setWeight(weight);
            ambiencePreference.setAmbienceEntity(ambienceEntity);
            this.ambiencePreferences.add(ambiencePreference);
        });
    }

    public void addWifiPreference(WifiEntity wifiEntity, Integer weight) {
        WifiPreference wp = new WifiPreference();
        wp.setWeight(weight);
        wp.setWifi(wifiEntity);
        this.wifiPreference = wp;
    }

    public void addAlcoholPreference(AlcoholEntity alcoholEntity, Integer weight) {
        AlcoholPreference ap = new AlcoholPreference();
        ap.setWeight(weight);
        ap.setAlcoholEntity(alcoholEntity);
        this.alcoholPreference = ap;
    }

    public void addCreditCardPreference(CreditCardEntity creditCardEntity, Integer weight) {
        CreditCardPreference cp = new CreditCardPreference();
        cp.setWeight(weight);
        cp.setCreditCardEntity(creditCardEntity);
        this.creditCardPreference = cp;
    }

    public void addOutdoorSeatingPreference(OutdoorSeatingEntity outdoorSeatingEntity, Integer weight) {
        OutdoorSeatingPreference op = new OutdoorSeatingPreference();
        op.setWeight(weight);
        op.setOutdoorSeatingEntity(outdoorSeatingEntity);
        this.outdoorSeatingPreference = op;
    }

    public void addRatingPreference(RatingEntity ratingEntity, Integer weight) {
        RatingPreference rp = new RatingPreference();
        rp.setWeight(weight);
        rp.setRatingEntity(ratingEntity);
        this.minimumRating = rp;
    }
}

