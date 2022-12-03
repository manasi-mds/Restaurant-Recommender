package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    Set<FollowUser> following = new HashSet<>();

    @Relationship(type = "LIKE_RESTAURANT", direction = OUTGOING)
    Set<LikeRestaurant> likedRestaurants =  new HashSet<>();

    public void followUser(UserEntity user2, Boolean follow) {
        if(this.following==null)
            this.following = new HashSet<>();

        if(follow) {
//            System.out.println("User "+this.name+" is following "+user2.name);
//            System.out.println(this.following.stream().map(UserEntity::getName).toList());
            //Preconditions.checkArgument(!this.following.contains(user2), "User "+this.name+" is already following "+user2.name);
//            Preconditions.checkArgument(!this.following.stream().map(UserEntity::getId).collect(Collectors.toSet()).contains(user2.getId()), "User is already following " + user2.getName());
            FollowUser followUser = new FollowUser();
            followUser.setUserEntity(user2);
            this.following.add(followUser);
        }
//        } else {
////            Preconditions.checkArgument(this.following.contains(user2), "User "+this.name+" is not following "+user2.name);
//            //Preconditions.checkArgument(this.following.stream().map(UserEntity::getId).collect(Collectors.toSet()).contains(user2.getId()), "User is not following " + user2.getName());
////            FollowUser followUser = new FollowUser();
////            followUser.setUserEntity(user2);
////            this.following.remove(followUser);
//        }
    }

    public void likeRestaurant(RestaurantEntity restaurant, boolean like) {
        if(this.likedRestaurants==null)
            this.likedRestaurants = new HashSet<>();

        if(like) {
            //Preconditions.checkArgument(!this.likedRestaurants.stream().map(RestaurantEntity::getId).collect(Collectors.toSet()).contains(restaurant.getId()), "User has already liked " + restaurant.getName());
            LikeRestaurant likeRestaurant = new LikeRestaurant();
            likeRestaurant.setRestaurantEntity(restaurant);
            this.likedRestaurants.add(likeRestaurant);
        } else {
            //Preconditions.checkArgument(this.likedRestaurants.stream().map(RestaurantEntity::getId).collect(Collectors.toSet()).contains(restaurant.getId()), "User has not liked " + restaurant.getName());
            //this.likedRestaurants.remove(restaurant);
        }
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

