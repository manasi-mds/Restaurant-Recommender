package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.RestaurantUserDTO;
import com.dbproject.restaurantrecommender.mapper.RestaurantMapper;
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

    private ArrayList<Double> createCosineForUser(UserEntity user) {
        return null;
    }

    private ArrayList<Double> createCosineForRestaurant(UserEntity user, RestaurantEntity restaurant) {
        return null;
    }

    private double calculateCosineSimilarity(ArrayList<Double> userVector, ArrayList<Double> restaurantVector) {
        return 0;
    }

}
