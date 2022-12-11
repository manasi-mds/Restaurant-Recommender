package com.dbproject.restaurantrecommender.services;

import com.dbproject.restaurantrecommender.dto.AmbienceDTO;
import com.dbproject.restaurantrecommender.mapper.AmbienceMapper;
import com.dbproject.restaurantrecommender.model.AmbienceEntity;
import com.dbproject.restaurantrecommender.repository.AmbienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AmbienceService implements IAmbienceService {

    private final AmbienceRepository ambienceRepository;

    @Override
    public List<AmbienceDTO> getAllAmbience() {
        //Preconditions.checkArgument(false, "Room Name cannot be empty");
        List<AmbienceEntity> ambienceEntities = ambienceRepository.findAll();
        return ambienceEntities.stream().map(AmbienceMapper::convert).collect(Collectors.toList());
    }
}
