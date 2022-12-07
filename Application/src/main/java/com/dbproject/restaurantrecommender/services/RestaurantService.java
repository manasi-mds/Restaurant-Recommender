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
        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        if(strictCuisinePreferences.isEmpty()) {
            restaurantEntities = restaurantRepository.findAll();
        }
        else{
            for(CuisinePreference scp : strictCuisinePreferences){
                restaurantEntities.addAll(restaurantRepository.havingACuisine(scp.getCuisineEntity().getId()));
            }
        }
        restaurantEntities = new HashSet<>(restaurantEntities).stream().toList();

        // Rating filter
        if(user.getMinimumRating() != null)
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasRating().getRating() >= user.getMinimumRating().getRatingEntity().getRating()).collect(Collectors.toList());

        // Remove closed restaurants
        restaurantEntities = restaurantEntities.stream().filter(RestaurantEntity::isOpen).collect(Collectors.toList());

        // Filter out already disliked restaurants
        Set<Long> dislikedRestaurants = user.getDislikedRestaurants().stream().map(dr -> dr.getRestaurantEntity().getId()).collect(Collectors.toSet());


        restaurantEntities = restaurantEntities.stream().filter(r-> !dislikedRestaurants.contains(r.getId())).collect(Collectors.toList()); // TODO: test, might not work

        if(strictAmbiencePreferences.size()>0)
            restaurantEntities = restaurantEntities.stream().filter(r -> !Collections.disjoint(r.getHasAmbiences().stream().map(BaseEntity::getId).collect(Collectors.toSet()), strictAmbiencePreferences.stream().map(ap->ap.getAmbienceEntity().getId()).collect(Collectors.toSet()))).collect(Collectors.toList());

        if(strictWifiPreference != null)
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasWifi()!=null && r.getHasWifi().getType().equals(strictWifiPreference.getWifi().getType())).collect(Collectors.toList());

        if(strictCreditCardPreference != null)
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getAcceptsCreditCard()!=null && r.getAcceptsCreditCard().getId().equals(strictCreditCardPreference.getCreditCardEntity().getId())).collect(Collectors.toList());

        if(strictOutdoorSeatingPreference != null)
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasOutdoorSeating()!=null && r.getHasOutdoorSeating().getId().equals(strictOutdoorSeatingPreference.getOutdoorSeatingEntity().getId())).collect(Collectors.toList());

        if(strictAlcoholPreference != null)
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasAlcohol()!=null && r.getHasAlcohol().getId().equals(strictAlcoholPreference.getAlcoholEntity().getId())).collect(Collectors.toList());

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

        //Outdoor_Seating
        if(user.getOutdoorSeatingPreference()!=null)
            userCosine.add(convertWeight(user.getOutdoorSeatingPreference().getWeight()));

        //Wifi_Preference
        if(user.getWifiPreference()!=null) {
            userCosine.add(convertWeight(user.getWifiPreference().getWeight()));
        }

        //Alcohol
        if(user.getAlcoholPreference()!=null) {
            userCosine.add(convertWeight(user.getAlcoholPreference().getWeight()));
        }

        //Credit_Card
        if(user.getCreditCardPreference()!=null) {
            userCosine.add(convertWeight(user.getCreditCardPreference().getWeight()));
        }

        //Ambience
        if(user.getAmbiencePreferences()!=null) {
            for (AmbiencePreference ap : user.getAmbiencePreferences()) {
                userCosine.add(convertWeight(ap.getWeight()));
            }
        }

        //Cuisine
        if(user.getCuisinePreferences()!=null) {
            for (CuisinePreference ap : user.getCuisinePreferences()) {
                userCosine.add(convertWeight(ap.getWeight()));
            }
        }

        return userCosine;
    }

    private ArrayList<Double> createCosineForRestaurant(UserEntity user, RestaurantEntity restaurant) {
        ArrayList<Double> restCosine = new ArrayList<>();

        //outdoor seating
        if(user.getOutdoorSeatingPreference()!=null) {
            if (restaurant.getHasOutdoorSeating() == null) {
                restCosine.add(0.0);
            } else {
                restCosine.add(1.0);
            }
        }

        //Wifi_Preference
        if(user.getWifiPreference()!=null) {
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

        // Alcohol
        if(user.getAlcoholPreference()!=null) {
            if(restaurant.getHasAlcohol()==null){
                restCosine.add(0.0);
            }
            if(restaurant.getHasAlcohol().isAlcoholServed()==user.getAlcoholPreference().getAlcoholEntity().isAlcoholServed()) {
                restCosine.add(1.0);
            }
            else {
                restCosine.add(0.0);
            }
        }


        // Credit_Card
        if(user.getCreditCardPreference()!=null) {
            if (restaurant.getAcceptsCreditCard() == null) {
                restCosine.add(0.0);
            } else {
                if (restaurant.getAcceptsCreditCard().isCreditCardAccepted() == user.getCreditCardPreference().getCreditCardEntity().isCreditCardAccepted()) {
                    restCosine.add(1.0);
                } else {
                    restCosine.add(0.0);
                }
            }
        }

        // Ambience
        if(user.getAmbiencePreferences()!=null) {
            for (AmbiencePreference ap : user.getAmbiencePreferences()) {
                if(restaurant.getHasAmbiences()==null){
                    restCosine.add(0.0);
                } else{
                    if(restaurant.getHasAmbiences().contains(ap.getAmbienceEntity())) {
                        restCosine.add(1.0);
                    }
                    else {
                        restCosine.add(0.0);
                    }
                }
            }
        }

        // Cuisine
        if(user.getCuisinePreferences()!=null) {
            for (CuisinePreference ap : user.getCuisinePreferences()) {
                if(restaurant.getHasCuisines()==null){
                    restCosine.add(0.0);
                } else {
                    if(restaurant.getHasCuisines().contains(ap.getCuisineEntity())) {
                        restCosine.add(1.0);
                    }
                    else {
                        restCosine.add(0.0);
                    }
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
        double cosineSim;
        if(normA==0 || normB==0) {
            cosineSim = 0.0;
        }
        else {
            cosineSim = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));

        }
        return cosineSim;
    }

    boolean isStrict(Integer weight){
        return weight == 5;
    }

}
