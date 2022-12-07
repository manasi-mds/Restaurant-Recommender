package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.RestaurantUserDTO;
import com.dbproject.restaurantrecommender.enums.WifiType;
import com.dbproject.restaurantrecommender.mapper.RestaurantMapper;
import com.dbproject.restaurantrecommender.model.AmbiencePreference;
import com.dbproject.restaurantrecommender.model.CuisinePreference;
import com.dbproject.restaurantrecommender.model.RestaurantEntity;
import com.dbproject.restaurantrecommender.model.UserEntity;
import com.dbproject.restaurantrecommender.model.*;
import com.dbproject.restaurantrecommender.respsitory.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.util.Preconditions;
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
        Set<Long> likedRestaurants = user.getLikedRestaurants().stream().map(lr -> lr.getRestaurantEntity().getId()).collect(Collectors.toSet());

        Preconditions.checkArgument(user.getCuisinePreferences().size()>0 && user.getAmbiencePreferences().size()>0, "User has not set preferences");

        // Figure out strict filtering parameters
        Set<CuisinePreference> strictCuisinePreferences = user.getCuisinePreferences().stream().filter(cp -> isStrict(cp.getWeight())).collect(Collectors.toSet());
        Set<AmbiencePreference> strictAmbiencePreferences = user.getAmbiencePreferences().stream().filter(ap -> isStrict(ap.getWeight())).collect(Collectors.toSet());
        WifiPreference strictWifiPreference = user.getWifiPreference() != null && isStrict(user.getWifiPreference().getWeight()) ? user.getWifiPreference() : null;
        CreditCardPreference strictCreditCardPreference = user.getCreditCardPreference() != null && isStrict(user.getCreditCardPreference().getWeight()) ? user.getCreditCardPreference() : null;
        OutdoorSeatingPreference strictOutdoorSeatingPreference = user.getOutdoorSeatingPreference() != null && isStrict(user.getOutdoorSeatingPreference().getWeight()) ? user.getOutdoorSeatingPreference() : null;
        AlcoholPreference strictAlcoholPreference = user.getAlcoholPreference() != null && isStrict(user.getAlcoholPreference().getWeight()) ? user.getAlcoholPreference() : null;

        // Initial selection
        List<RestaurantEntity> restaurantEntities;
        restaurantEntities = strictCuisinePreferences.isEmpty() ? restaurantRepository.findAll():
                restaurantRepository.findByHasCuisines(strictCuisinePreferences.stream().map(CuisinePreference::getCuisineEntity).collect(Collectors.toSet()));

        // Rating filter
        if(user.getMinimumRating() != null)
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasRating().getRating() >= user.getMinimumRating().getRatingEntity().getRating()).collect(Collectors.toList());

        // Remove closed restaurants
        restaurantEntities = restaurantEntities.stream().filter(RestaurantEntity::isOpen).collect(Collectors.toList());

        // Filter out already disliked restaurants
        Set<RestaurantEntity> dislikedRestaurants = user.getDislikedRestaurants().stream().map(DislikeRestaurant::getRestaurantEntity).collect(Collectors.toSet());
        restaurantEntities.removeAll(dislikedRestaurants); // TODO: test, might not work

        // TODO: test, might not work
        for(AmbiencePreference ap : strictAmbiencePreferences) {
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasAmbiences().contains(ap.getAmbienceEntity())).collect(Collectors.toList());
        }
        if(strictWifiPreference != null) {
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasWifi()!=null && r.getHasWifi().getType().equals(strictWifiPreference.getWifi().getType())).collect(Collectors.toList());
        }
        if(strictCreditCardPreference != null) {
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getAcceptsCreditCard()!=null && r.getAcceptsCreditCard().getId().equals(strictCreditCardPreference.getCreditCardEntity().getId())).collect(Collectors.toList());
        }
        if(strictOutdoorSeatingPreference != null) {
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasOutdoorSeating()!=null && r.getHasOutdoorSeating().getId().equals(strictOutdoorSeatingPreference.getOutdoorSeatingEntity().getId())).collect(Collectors.toList());
        }
        if(strictAlcoholPreference != null) {
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasAlcohol()!=null && r.getHasAlcohol().getId().equals(strictAlcoholPreference.getAlcoholEntity().getId())).collect(Collectors.toList());
        }
        return restaurantEntities.stream()
                .map(r -> RestaurantMapper.convertToUserDTO(r, likedRestaurants, null, calculateCosineSimilarity(createCosineForUser(user), createCosineForRestaurant(user, r))))
                .sorted(Comparator.comparing(RestaurantUserDTO::getCosineSimilarity, Comparator.reverseOrder())).toList();
    }

    //1->0.2
    //2->0.4
    //3->0.6
    //4->0.8
    //5->1.0
    private double convertWeight(int weight) {
        return (double) weight /5.0;
    }

    private ArrayList<Double> createCosineForUser(UserEntity user) {
        ArrayList<Double> userCosine = new ArrayList<>();

        //cosine similarity order
        //Outdoor_Seating
        if(user.getOutdoorSeatingPreference()==null)
            userCosine.add(0.0);
        else
            userCosine.add(convertWeight(user.getOutdoorSeatingPreference().getWeight()));

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
            for (AmbiencePreference ap : user.getAmbiencePreferences()) {
                if(restaurant.getHasAmbiences().contains(ap.getAmbienceEntity())) {
                    restCosine.add(1.0);
                }
                else {
                    restCosine.add(0.0);
                }
            }
        }

        //cuisine
        if(user.getCuisinePreferences()==null) {
            restCosine.add(0.0);
        }
        else {
            //double cui = 0.0;
            for (CuisinePreference ap : user.getCuisinePreferences()) {
                if(restaurant.getHasCuisines().contains(ap.getCuisineEntity())) {
                    restCosine.add(1.0);
                }
                else {
                    restCosine.add(0.0);
                }
            }
        }
        return restCosine;
    }

    private Double calculateCosineSimilarity(ArrayList<Double> userVector, ArrayList<Double> restaurantVector) {
        Preconditions.checkArgument(userVector.size()==restaurantVector.size(), "Vectors should be of the same size");
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

    boolean isStrict(Integer weight){
        return weight == 5;
    }

}
