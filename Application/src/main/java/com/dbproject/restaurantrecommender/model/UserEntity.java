package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

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

    @Relationship(type = "DISLIKE_RESTAURANT", direction = OUTGOING)
    Set<DislikeRestaurant> dislikedRestaurants =  new HashSet<>();

    @Property("max_distance")
    Double distancePreference;

    public void followUser(UserEntity user2, Boolean follow) {
        if(this.following==null)
            this.following = new HashSet<>();

        if(follow) {
            Preconditions.checkArgument(this.following.stream().noneMatch(fu-> Objects.equals(fu.getUserEntity().getId(), user2.getId())), "Already following user " + user2.getName());
            FollowUser followUser = new FollowUser();
            followUser.setUserEntity(user2);
            this.following.add(followUser);
        } else {
            Optional<FollowUser> user2Follow = this.following.stream()
                    .filter(followUser -> followUser.getUserEntity().getId().equals(user2.getId()))
                    .findAny();
            Preconditions.checkArgument(user2Follow.isPresent(), "Not following user " + user2.getName());
            this.following.remove(user2Follow.get());
        }
    }

    public void likeRestaurant(RestaurantEntity restaurant, boolean like) {
        if(this.likedRestaurants==null)
            this.likedRestaurants = new HashSet<>();

        if(like) {
            Preconditions.checkArgument(this.likedRestaurants.stream().noneMatch(lr -> lr.getRestaurantEntity().getId().equals(restaurant.getId())), "Already liked restaurant " + restaurant.getName());
            LikeRestaurant likeRestaurant = new LikeRestaurant();
            likeRestaurant.setRestaurantEntity(restaurant);
            this.likedRestaurants.add(likeRestaurant);
        } else {
            Optional<LikeRestaurant> likedRest = this.likedRestaurants.stream()
                    .filter(user -> user.getRestaurantEntity().getId().equals(restaurant.getId()))
                    .findAny();
            Preconditions.checkArgument(likedRest.isPresent(), "Does not already like restaurant " + restaurant.getId() );
            this.likedRestaurants.remove(likedRest.get());
        }
    }

    public void dislikeRestaurant(RestaurantEntity restaurant, boolean dislike) {
        if(this.dislikedRestaurants==null)
            this.dislikedRestaurants = new HashSet<>();

        if(dislike) {
            Preconditions.checkArgument(this.dislikedRestaurants.stream().noneMatch(lr -> lr.getRestaurantEntity().getId().equals(restaurant.getId())), "Already disliked restaurant " + restaurant.getName());
            DislikeRestaurant dislikeRestaurant = new DislikeRestaurant();
            dislikeRestaurant.setRestaurantEntity(restaurant);
            this.dislikedRestaurants.add(dislikeRestaurant);
        } else {
            Optional<DislikeRestaurant> dislikedRest = this.dislikedRestaurants.stream()
                    .filter(user -> user.getRestaurantEntity().getId().equals(restaurant.getId()))
                    .findAny();
            Preconditions.checkArgument(dislikedRest.isPresent(), "Does not already like restaurant " + restaurant.getId() );
            this.dislikedRestaurants.remove(dislikedRest.get());
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

    public void addRatingPreference(RatingEntity ratingEntity) {
        RatingPreference rp = new RatingPreference();
        rp.setRatingEntity(ratingEntity);
        this.minimumRating = rp;
    }

    public void addDistancePreference(Double distance) {
        this.distancePreference = distance;
    }
}

