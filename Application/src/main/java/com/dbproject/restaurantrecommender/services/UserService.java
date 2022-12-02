package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.UserDTO;
import com.dbproject.restaurantrecommender.mapper.UserMapper;
import com.dbproject.restaurantrecommender.model.RestaurantEntity;
import com.dbproject.restaurantrecommender.model.UserEntity;
import com.dbproject.restaurantrecommender.respsitory.RestaurantRepository;
import com.dbproject.restaurantrecommender.respsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.util.Preconditions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    UserEntity verifyUser(Long userId){
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        Preconditions.checkArgument(optionalUser.isPresent(), "User not found");
        return optionalUser.get();
    }



}
