package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.CuisineDTO;
import com.dbproject.restaurantrecommender.mapper.CuisineMapper;
import com.dbproject.restaurantrecommender.model.CuisineEntity;
import com.dbproject.restaurantrecommender.repository.CuisineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuisineService implements ICuisineService{

    private final CuisineRepository cuisineRepository;

    @Override
    public List<CuisineDTO> getAllCuisines() {
        List<CuisineEntity> cuisineEntities = cuisineRepository.findAll();
        return cuisineEntities.stream().map(CuisineMapper::convert).collect(Collectors.toList());
    }
}
