package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.RestaurantUserDTO;
import com.dbproject.restaurantrecommender.mapper.RestaurantMapper;
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
        RatingPreference strictRatingPreference = user.getMinimumRating() != null && isStrict(user.getMinimumRating().getWeight()) ? user.getMinimumRating() : null;

        // Initial selection
        List<RestaurantEntity> restaurantEntities;
        restaurantEntities = strictCuisinePreferences.isEmpty() ? restaurantRepository.findAll():
                restaurantRepository.findByHasCuisines(strictCuisinePreferences.stream().map(CuisinePreference::getCuisineEntity).collect(Collectors.toSet()));

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
        if(strictRatingPreference != null) {
            restaurantEntities = restaurantEntities.stream().filter(r -> r.getHasRating().getRating() >= strictRatingPreference.getRatingEntity().getRating()).collect(Collectors.toList());
        }

        return restaurantEntities.stream()
                .map(r -> RestaurantMapper.convertToUserDTO(r, likedRestaurants, null, calculateCosineSimilarity(createCosineForUser(user), createCosineForRestaurant(user, r))))
                .sorted(Comparator.comparing(RestaurantUserDTO::getCosineSimilarity, Comparator.reverseOrder())).toList();
    }

    private ArrayList<Double> createCosineForUser(UserEntity user) {
        return null;
    }

    private ArrayList<Double> createCosineForRestaurant(UserEntity user, RestaurantEntity restaurant) {
        return null;
    }

    private Double calculateCosineSimilarity(ArrayList<Double> userVector, ArrayList<Double> restaurantVector) {
        return null;
    }

    boolean isStrict(Integer weight){
        return weight == 5;
    }

}
