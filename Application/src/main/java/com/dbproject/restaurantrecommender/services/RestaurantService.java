package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.RestaurantUserDTO;
import com.dbproject.restaurantrecommender.enums.WifiType;
import com.dbproject.restaurantrecommender.mapper.RestaurantMapper;
import com.dbproject.restaurantrecommender.model.AmbiencePreference;
import com.dbproject.restaurantrecommender.model.CuisinePreference;
import com.dbproject.restaurantrecommender.model.RestaurantEntity;
import com.dbproject.restaurantrecommender.model.UserEntity;
import com.dbproject.restaurantrecommender.respsitory.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

    @Override
    public List<RestaurantUserDTO> getAllRestaurants(Long userId) {
        // TODO: verify the user
        // we will get the liked and disliked
        // set -> like and dislike - two sets
        Set<Long> likedRestaurants = new HashSet<>();
        Set<Long> dislikedRestaurants = new HashSet<>();
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();
        return restaurantEntities.stream().map(r-> RestaurantMapper.convertToUserDTO(r, likedRestaurants, dislikedRestaurants, null)).collect(Collectors.toList());
    }

    @Override
    public Optional<RestaurantDTO> getRestaurantById(Long restaurantId) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(restaurantId);
        return restaurantEntity.map(RestaurantMapper::convert);
    }

    @Override
    public List<RestaurantUserDTO> getPreferredRestaurants(Long userId) {
        UserEntity user = userService.verifyUser(userId);
        // Figure out strict filtering parameters
        // filter out already disliked restaurants
        // fetch restaurants based on those parameters ... if no strict filtering, all restaurants

        // cosine similarity - ordering based on that
        ArrayList<Double> userVector = createCosineForUser(user);

        // we will get the liked
        // return dto :)
        return null;
    }

    private double convertWeight(int weight) {
        return Double.valueOf(weight)/5.0;
    }

    private ArrayList<Double> createCosineForUser(UserEntity user) {
        ArrayList<Double> userCosine = new ArrayList<>();

        //0->0
        //1->0.2
        //2->0.4
        //3->0.6
        //4->0.8
        //5->1
        //cosine similarity order
        //Outdoor_Seating
        if(user.getOutdoorSeatingPreference()==null)
            userCosine.add(0.0);
        else
            userCosine.add(convertWeight(user.getOutdoorSeatingPreference().getWeight()));
        //Minimum_Rating

        if(user.getMinimumRating()==null)
            userCosine.add(0.0);
        else
            userCosine.add(convertWeight(user.getMinimumRating().getWeight()));

        //Wifi_Preference

        if(user.getWifiPreference()==null)
            userCosine.add(0.0);
        else
            userCosine.add(convertWeight(user.getWifiPreference().getWeight()));


        //Alcohol

        if(user.getAlcoholPreference()==null)
            userCosine.add(0.0);
        else
            userCosine.add(convertWeight(user.getAlcoholPreference().getWeight()));
        //Credit_Card

        if(user.getCreditCardPreference()==null)
            userCosine.add(0.0);
        else
            userCosine.add(convertWeight(user.getCreditCardPreference().getWeight()));

        //Ambience
        if(user.getAmbiencePreferences()==null)
            userCosine.add(0.0);
        else {
            for (AmbiencePreference ap : user.getAmbiencePreferences()) {
                userCosine.add(convertWeight(ap.getWeight()));
            }
        }


        //Cuisine
        if(user.getCuisinePreferences()==null)
            userCosine.add(0.0);
        else {
            for (CuisinePreference ap : user.getCuisinePreferences()) {
                userCosine.add(convertWeight(ap.getWeight()));
            }
        }

        return userCosine;
    }

    private ArrayList<Double> createCosineForRestaurant(UserEntity user, RestaurantEntity restaurant) {
        ArrayList<Double> restCosine = new ArrayList<>();

        //outdoor seating
        if(user.getOutdoorSeatingPreference()==null) {
            restCosine.add(0.0);
        }
        else {
            if(restaurant.getHasOutdoorSeating()==null) {
                restCosine.add(0.0);
            }
            else {
                restCosine.add(1.0);
            }
        }

        //minimum rating
        if(user.getMinimumRating()==null) {
            restCosine.add(0.0);
        }
        else {
            double rating = restaurant.getHasRating().getRating();
            if(rating>=user.getMinimumRating().getRatingEntity().getRating()) {
                restCosine.add(1.0);
            }
            else {
                restCosine.add(0.0);
            }
        }

        //Wifi_Preference
        if(user.getWifiPreference()==null) {
            restCosine.add(0.0);
        }
        else {
            if(restaurant.getHasWifi()==null) {
                restCosine.add(0.0);
            }
            else {
                WifiType type = restaurant.getHasWifi().getType();
                if(user.getWifiPreference().getWifi().getType().equals(type)) {
                    restCosine.add(1.0);
                }
                else {
                    restCosine.add(0.0);
                }
            }

        }


        //Alcohol

        if(user.getAlcoholPreference()==null) {
            restCosine.add(0.0);
        }
        else {
            if(restaurant.getHasAlcohol().isAlcoholServed()==user.getAlcoholPreference().getAlcoholEntity().isAlcoholServed()) {
                restCosine.add(1.0);
            }
            else {
                restCosine.add(0.0);
            }
        }

        //Credit_Card

        if(user.getCreditCardPreference()==null) {
            restCosine.add(0.0);
        }
        else {
            if(restaurant.getAcceptsCreditCard().isCreditCardAccepted() ==user.getCreditCardPreference().getCreditCardEntity().isCreditCardAccepted()) {
                restCosine.add(1.0);
            }
            else {
                restCosine.add(0.0);
            }
        }


        //Ambience

        if(user.getAmbiencePreferences()==null) {
            restCosine.add(0.0);
        }
        else {
            double amb = 0.0;
            for (AmbiencePreference ap : user.getAmbiencePreferences()) {
                if(restaurant.getHasAmbiences().contains(ap)) {
                    amb = 1.0;
                }
            }
            restCosine.add(amb);

        }


        //cuisine

        if(user.getCuisinePreferences()==null) {
            restCosine.add(0.0);
        }
        else {
            double cui = 0.0;
            for (CuisinePreference ap : user.getCuisinePreferences()) {
                if(restaurant.getHasCuisines().contains(ap)) {
                    cui = 1.0;
                }
            }
            restCosine.add(cui);
        }





        return restCosine;
    }

    private Double calculateCosineSimilarity(ArrayList<Double> userVector, ArrayList<Double> restaurantVector) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < userVector.size(); i++) {
            dotProduct += userVector.get(i) * restaurantVector.get(i);
            normA += Math.pow(userVector.get(i), 2);
            normB += Math.pow(restaurantVector.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}
