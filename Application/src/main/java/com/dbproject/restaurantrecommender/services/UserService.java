package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.UserDTO;
import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;
import com.dbproject.restaurantrecommender.mapper.RestaurantMapper;
import com.dbproject.restaurantrecommender.mapper.UserMapper;
import com.dbproject.restaurantrecommender.model.RestaurantEntity;
import com.dbproject.restaurantrecommender.model.UserEntity;
import com.dbproject.restaurantrecommender.respsitory.RestaurantRepository;
import com.dbproject.restaurantrecommender.respsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = UserMapper.create(userDTO);
        userEntity = userRepository.save(userEntity);
        return UserMapper.convert(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::convert).toList();
    }

    public void followUser(Long userId, Long followUserId) {
        Preconditions.checkArgument(Objects.equals(userId, followUserId), "User cannot follow itself");
        UserEntity user1 = verifyUser(userId);
        UserEntity user2 = verifyUser(followUserId);
        user1.followUser(user2);
        userRepository.save(user1);
    }

    @Override
    public List<UserDTO> getFollowedUsers(Long userId) {
        UserEntity user = verifyUser(userId);
        return user.getFollowing().stream().map(UserMapper::convert).toList();
    }

    @Override
    public void likeRestaurant(Long userId, Long restaurantId) {
        UserEntity user = verifyUser(userId);
        Optional<RestaurantEntity> optionalRestaurant  = restaurantRepository.findById(restaurantId);
        Preconditions.checkArgument(optionalRestaurant.isPresent(), "Restaurant with id " + restaurantId +"  does not exist" );
        RestaurantEntity restaurant = optionalRestaurant.get();
        user.likeRestaurant(restaurant);
        userRepository.save(user);
    }

    @Override
    public List<RestaurantDTO> getLikedRestaurants(Long userId) {
        UserEntity user = verifyUser(userId);
        return user.getLikedRestaurants().stream().map(RestaurantMapper::convert).toList();
    }

    @Override
    public List<UserDTO> getPotentialFriends(Long userId) {
        UserEntity user = verifyUser(userId);
        List<RestaurantDTO> likedRestaurants = getLikedRestaurants(userId);
        // TODO: Potential friends are users who like the same restaurants as the user

        return null;
    }

    @Override
    public List<RestaurantDTO> getPotentialRestaurants(Long userId) {
        UserEntity user = verifyUser(userId);
        Set<UserEntity> friends = user.getFollowing();
        Set<RestaurantEntity> potentialRestaurants = friends.stream().map(UserEntity::getLikedRestaurants).flatMap(Set::stream).collect(Collectors.toSet());
        return potentialRestaurants.stream().map(RestaurantMapper::convert).toList();
    }

    @Override
    public void createPreference(Long userId, UserPreferenceDTO userPreferenceDTO) {

    }

    UserEntity verifyUser(Long userId){
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        Preconditions.checkArgument(optionalUser.isPresent(), "User not found");
        return optionalUser.get();
    }

}
