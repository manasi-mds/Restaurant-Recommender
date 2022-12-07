package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.UserDTO;
import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;
import com.dbproject.restaurantrecommender.dto.preference.AmbiencePreferenceDTO;
import com.dbproject.restaurantrecommender.dto.preference.CuisinePreferenceDTO;
import com.dbproject.restaurantrecommender.enums.WifiType;
import com.dbproject.restaurantrecommender.mapper.RestaurantMapper;
import com.dbproject.restaurantrecommender.mapper.UserMapper;
import com.dbproject.restaurantrecommender.model.*;
import com.dbproject.restaurantrecommender.respsitory.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final CuisineRepository cuisineRepository;
    private final AmbienceRepository ambienceRepository;
    private final WifiRepository wifiRepository;
    private final AlcoholRepository alcoholRepository;
    private final CreditCardRepository creditCardRepository;
    private final OutdoorSeatingRepository outdoorSeatingRepository;
    private final RatingRepository ratingRepository;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = UserMapper.create(userDTO);
        userEntity = userRepository.save(userEntity);
        return UserMapper.convert(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::convert).toList();
    }

    @Override
    @Transactional
    public void followUser(Long userId, Long followUserId, Boolean follow) {
        Preconditions.checkArgument(!Objects.equals(userId, followUserId), "User cannot follow itself");
        UserEntity user1 = verifyUser(userId);
        UserEntity user2 = verifyUser(followUserId);
        user1.followUser(user2, follow);
        userRepository.save(user1);
    }

    @Override
    public List<UserDTO> getFollowedUsers(Long userId) {
        UserEntity user = verifyUser(userId);
        return user.getFollowing().stream().map(f -> UserMapper.convert(f.getUserEntity())).toList();
    }

    @Override
    @Transactional
    public void likeRestaurant(Long userId, Long restaurantId, Boolean like) {
        UserEntity user = verifyUser(userId);
        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Preconditions.checkArgument(optionalRestaurant.isPresent(), "Restaurant with id " + restaurantId + "  does not exist");
        RestaurantEntity restaurant = optionalRestaurant.get();
        user.likeRestaurant(restaurant, like);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void dislikeRestaurant(Long userId, Long restaurantId, Boolean like) {
        UserEntity user = verifyUser(userId);
        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Preconditions.checkArgument(optionalRestaurant.isPresent(), "Restaurant with id " + restaurantId + "  does not exist");
        RestaurantEntity restaurant = optionalRestaurant.get();
        user.dislikeRestaurant(restaurant, like);
        userRepository.save(user);
    }

    @Override
    public List<RestaurantDTO> getLikedRestaurants(Long userId) {
        UserEntity user = verifyUser(userId);
        return user.getLikedRestaurants().stream().map(lr -> RestaurantMapper.convert(lr.getRestaurantEntity())).toList();
    }

    @Override
    public List<UserDTO> getPotentialFriends(Long userId) {
        UserEntity user = verifyUser(userId);
        Set<Long> filteredUsers = user.getFollowing().stream().map(fu-> fu.getUserEntity().getId()).collect(Collectors.toSet());
        filteredUsers.add(user.getId());

        List<RestaurantDTO> likedRestaurants = user.getLikedRestaurants().stream().map(lr -> RestaurantMapper.convert(lr.getRestaurantEntity())).toList();
        Set<Long> restaurantIds = likedRestaurants.stream().map(RestaurantDTO::getId).collect(Collectors.toSet());

        Set<UserEntity> potentialFriends = userRepository.getPotentialFriends(restaurantIds.stream().toList());

        potentialFriends.removeIf(pf-> filteredUsers.contains(pf.getId()));
        return potentialFriends.stream().map(UserMapper::convert).toList();
    }

    @Override
    public List<RestaurantDTO> getPotentialRestaurants(Long userId) {
        UserEntity user = verifyUser(userId);
        Set<UserEntity> friends = user.getFollowing().stream().map(FollowUser::getUserEntity).collect(Collectors.toSet());
        Set<RestaurantEntity> potentialRestaurants = friends.stream().map(friend-> friend.getLikedRestaurants().stream().map(LikeRestaurant::getRestaurantEntity).collect(Collectors.toSet())).flatMap(Collection::stream).collect(Collectors.toSet());
        return potentialRestaurants.stream().map(RestaurantMapper::convert).toList();
    }

    @Override
    @Transactional
    public void createPreference(Long userId, UserPreferenceDTO userPreferenceDTO) {
        UserEntity user = verifyUser(userId);
        addCuisinePreference(userPreferenceDTO, user);
        addAmbiencePreference(userPreferenceDTO, user);
        addWifiPreference(userPreferenceDTO, user);
        addAlcoholPreference(userPreferenceDTO, user);
        addCreditCardPreference(userPreferenceDTO, user);
        addOutdoorSeatingPreference(userPreferenceDTO, user);
        addRatingPreference(userPreferenceDTO, user);
        addDistancePreference(userPreferenceDTO, user);
        userRepository.save(user);
    }

    private void addDistancePreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if (userPreferenceDTO.getDistance() == null)
            return;
        Preconditions.checkArgument(userPreferenceDTO.getDistance() > 0, "Distance in miles cannot be less than 0");
        user.addDistancePreference(userPreferenceDTO.getDistance());
    }

    private void addRatingPreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if(userPreferenceDTO.getMinimumRating()==null && userPreferenceDTO.getMinimumRating().getMinRating() == null) {
            user.setMinimumRating(null);
            return;
        }

        checkRating(userPreferenceDTO.getMinimumRating().getMinRating());
        Optional<RatingEntity> ratingEntity = ratingRepository.findByRating(userPreferenceDTO.getMinimumRating().getMinRating());
        Preconditions.checkArgument(ratingEntity.isPresent(), "Rating info " + userPreferenceDTO.getMinimumRating().getMinRating() + " does not exist in the database");
        user.addRatingPreference(ratingEntity.get());
    }

    private void addOutdoorSeatingPreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if(userPreferenceDTO.getOutdoorSeating()==null && userPreferenceDTO.getOutdoorSeating().getIsOutdoorSeatingAvailable() == null) {
            user.setOutdoorSeatingPreference(null);
            return;
        }

        String outdoorSeating = userPreferenceDTO.getOutdoorSeating().getIsOutdoorSeatingAvailable() ? "true" : null;
        if(outdoorSeating!=null) {
            Optional<OutdoorSeatingEntity> outdoorSeatingEntity = outdoorSeatingRepository.findByName(outdoorSeating);
            Preconditions.checkArgument(outdoorSeatingEntity.isPresent(), "Outdoor seating info " + outdoorSeating + " does not exist in the database");
            user.addOutdoorSeatingPreference(outdoorSeatingEntity.get(), userPreferenceDTO.getOutdoorSeating().getWeight());
        }
    }

    private void addCreditCardPreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if(userPreferenceDTO.getCreditCardAccepted() == null && userPreferenceDTO.getCreditCardAccepted().getIsCreditCardAccepted() == null) {
            user.setCreditCardPreference(null);
            return;
        }

        String creditCardAccepted = userPreferenceDTO.getCreditCardAccepted().getIsCreditCardAccepted() ? "yes" : null;
        if(creditCardAccepted!=null) {
            Optional<CreditCardEntity> creditCardEntity = creditCardRepository.findByName(creditCardAccepted);
            Preconditions.checkArgument(creditCardEntity.isPresent(), "Credit card accepted info " + creditCardAccepted + " does not exist in the database");
            user.addCreditCardPreference(creditCardEntity.get(), userPreferenceDTO.getCreditCardAccepted().getWeight());
        }
    }

    private void addAlcoholPreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if (userPreferenceDTO.getAlcoholServed() == null && userPreferenceDTO.getAlcoholServed().getIsAlcoholServed() == null){
            user.setAlcoholPreference(null);
            return;
        }

        String alcoholServed = userPreferenceDTO.getAlcoholServed().getIsAlcoholServed() ? "yes" : "no";
        Optional<AlcoholEntity> alcoholEntity = alcoholRepository.findByName(alcoholServed);
        Preconditions.checkArgument(alcoholEntity.isPresent(), "Alcohol served info " + alcoholServed + " does not exist in the database");
        user.addAlcoholPreference(alcoholEntity.get(), userPreferenceDTO.getAlcoholServed().getWeight());
    }

    private void addWifiPreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if(userPreferenceDTO.getWifiTypeAvailable() == null || StringUtils.isNotBlank(userPreferenceDTO.getWifiTypeAvailable().getWifiType())) {
            user.setWifiPreference(null);
            return;
        }

        WifiType type = switch (userPreferenceDTO.getWifiTypeAvailable().getWifiType().toLowerCase()) {
            case "free" -> WifiType.free;
            case "paid" -> WifiType.paid; // TODO: should also include free
            default -> throw new IllegalArgumentException("Invalid wifi type");
        };
        Optional<WifiEntity> wifiEntity = wifiRepository.findByType(type);
        Preconditions.checkArgument(wifiEntity.isPresent(), "Wifi type " + type + " does not exist in the database");
        user.addWifiPreference(wifiEntity.get(), userPreferenceDTO.getWifiTypeAvailable().getWeight());

    }

    private void addAmbiencePreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if(userPreferenceDTO.getAmbiences() == null || userPreferenceDTO.getAmbiences().isEmpty()) {
            user.setAmbiencePreferences(null);
            return;
        }

        List<AmbienceEntity> ambiences;
        Map<Long, Integer> ambienceIdWithWeights = userPreferenceDTO.getAmbiences().stream().collect(Collectors.toMap(AmbiencePreferenceDTO::getAmbienceId, AmbiencePreferenceDTO::getWeight));
        ambiences = ambienceRepository.findAllById(userPreferenceDTO.getAmbiences().stream().map(AmbiencePreferenceDTO::getAmbienceId).collect(Collectors.toList()));
        Map<AmbienceEntity, Integer> preferredAmbienceWeight = ambiences.stream().collect(Collectors.toMap(ambience -> ambience, ambience -> ambienceIdWithWeights.get(ambience.getId())));
        user.addAmbiencePreferences(preferredAmbienceWeight);
    }

    private void addCuisinePreference(UserPreferenceDTO userPreferenceDTO, UserEntity user) {
        if (userPreferenceDTO.getCuisines() == null || userPreferenceDTO.getCuisines().isEmpty()) {
            user.setCuisinePreferences(null);
            return;
        }

        List<CuisineEntity> cuisines;
        Map<Long, Integer> cuisineIdWithWeights = userPreferenceDTO.getCuisines().stream().collect(Collectors.toMap(CuisinePreferenceDTO::getCuisineId, CuisinePreferenceDTO::getWeight));
        cuisines = cuisineRepository.findAllById(userPreferenceDTO.getCuisines().stream().map(CuisinePreferenceDTO::getCuisineId).collect(Collectors.toList()));
        Map<CuisineEntity, Integer> preferredCuisineWeight = cuisines.stream().collect(Collectors.toMap(cuisine -> cuisine, cuisine -> cuisineIdWithWeights.get(cuisine.getId())));
        user.addCuisinePreferences(preferredCuisineWeight);

    }

    private void checkRating(Double minRating) {
        Preconditions.checkArgument(minRating >= 1 && minRating <= 5, "Rating should be between 1 and 5");
        Preconditions.checkArgument(minRating % 0.5 == 0, "Rating should be in increments of 0.5");
    }

    public UserEntity verifyUser(Long userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        Preconditions.checkArgument(optionalUser.isPresent(), "User not found");
        return optionalUser.get();
    }

}
