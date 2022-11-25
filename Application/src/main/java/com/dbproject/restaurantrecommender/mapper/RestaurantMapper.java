package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.model.RestaurantEntity;

import java.util.stream.Collectors;

public class RestaurantMapper {
    public static RestaurantDTO convert(RestaurantEntity restaurantEntity) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurantEntity.getId());
        restaurantDTO.setName(restaurantEntity.getName());
        restaurantDTO.setAddress(restaurantEntity.getAddress());
        restaurantDTO.setBusinessId(restaurantEntity.getBusinessId());
        restaurantDTO.setLatitude(restaurantEntity.getLatitude());
        restaurantDTO.setLongitude(restaurantEntity.getLongitude());
        restaurantDTO.setReviewCount(restaurantEntity.getReviewCount());
        restaurantDTO.setHoursMon(restaurantEntity.getHoursMon());
        restaurantDTO.setHoursTue(restaurantEntity.getHoursTue());
        restaurantDTO.setHoursWed(restaurantEntity.getHoursWed());
        restaurantDTO.setHoursThu(restaurantEntity.getHoursThu());
        restaurantDTO.setHoursFri(restaurantEntity.getHoursFri());
        restaurantDTO.setHoursSat(restaurantEntity.getHoursSat());
        restaurantDTO.setHoursSun(restaurantEntity.getHoursSun());
        restaurantDTO.setCuisines(restaurantEntity.getHasCuisines().stream().map(CuisineMapper::convert).collect(Collectors.toList()));
        restaurantDTO.setAmbiences(restaurantEntity.getHasAmbiences().stream().map(AmbienceMapper::convert).collect(Collectors.toList()));
        restaurantDTO.setIsAlcoholServed(restaurantEntity.getHasAlcohol().isAlcoholServed());
        restaurantDTO.setWifi(restaurantEntity.getHasWifi().getType());
        restaurantDTO.setRating(restaurantEntity.getHasRating().getRating());
        restaurantDTO.setIsCreditCardAccepted(restaurantEntity.getAcceptsCreditCard().isCreditCardAccepted());
        restaurantDTO.setIsOpen(restaurantEntity.isOpen());
        restaurantDTO.setIsOutdoorSeatingAvailable(restaurantEntity.getHasOutdoorSeating().isOutdoorSeatingAvailable());
        return restaurantDTO;
    }
}
