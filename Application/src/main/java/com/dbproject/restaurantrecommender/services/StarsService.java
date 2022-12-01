//package com.dbproject.restaurantrecommender.services;
//
//import com.dbproject.restaurantrecommender.dto.StarsDTO;
//import com.dbproject.restaurantrecommender.mapper.StarsMapper;
//import com.dbproject.restaurantrecommender.model.StarsEntity;
//import com.dbproject.restaurantrecommender.respsitory.StarsRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class StarsService implements IStarsService{
//    private final StarsRepository starsRepository;
//
//    @Override
//    public List<StarsDTO> getAllStars() {
//        //Preconditions.checkArgument(false, "Room Name cannot be empty");
//        List<StarsEntity> starsEntities = starsRepository.findAll();
//        return starsEntities.stream().map(StarsMapper::convert).collect(Collectors.toList());
//    }
//}
