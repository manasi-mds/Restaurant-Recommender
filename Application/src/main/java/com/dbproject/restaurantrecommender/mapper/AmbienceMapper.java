package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.AmbienceDTO;
import com.dbproject.restaurantrecommender.model.AmbienceEntity;

public class AmbienceMapper {
    public static AmbienceDTO convert(AmbienceEntity ambienceEntity) {
        AmbienceDTO ambienceDTO = new AmbienceDTO();
        ambienceDTO.setId(ambienceEntity.getId());
        ambienceDTO.setName(ambienceEntity.getName());
        return ambienceDTO;
    }
}
