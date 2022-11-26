package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.StarsDTO;

import java.util.List;

public interface IStarsService {
    List<StarsDTO> getAllStars();
}
