package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.RestaurantDTO;
import com.dbproject.restaurantrecommender.dto.RestaurantUserDTO;
import com.dbproject.restaurantrecommender.model.RestaurantEntity;

import java.util.Set;
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
        if(restaurantEntity.getHoursMon()!=null) restaurantDTO.getHours().put("Monday", restaurantEntity.getHoursMon());
        if(restaurantEntity.getHoursTue()!=null) restaurantDTO.getHours().put("Tuesday", restaurantEntity.getHoursTue());
        if(restaurantEntity.getHoursWed()!=null) restaurantDTO.getHours().put("Wednesday", restaurantEntity.getHoursWed());
        if(restaurantEntity.getHoursThu()!=null) restaurantDTO.getHours().put("Thursday", restaurantEntity.getHoursThu());
        if(restaurantEntity.getHoursFri()!=null) restaurantDTO.getHours().put("Friday", restaurantEntity.getHoursFri());
        if(restaurantEntity.getHoursSat()!=null) restaurantDTO.getHours().put("Saturday", restaurantEntity.getHoursSat());
        if(restaurantEntity.getHoursSun()!=null) restaurantDTO.getHours().put("Sunday", restaurantEntity.getHoursSun());
        restaurantDTO.setCuisines(restaurantEntity.getHasCuisines().stream().map(CuisineMapper::convert).collect(Collectors.toList()));
        restaurantDTO.setAmbiences(restaurantEntity.getHasAmbiences().stream().map(AmbienceMapper::convert).collect(Collectors.toList()));
        restaurantDTO.setIsAlcoholServed(restaurantEntity.getHasAlcohol().isAlcoholServed());
        restaurantDTO.setWifi( restaurantEntity.getHasWifi() == null ? null : restaurantEntity.getHasWifi().getType());
        restaurantDTO.setRating(restaurantEntity.getHasRating() == null ? null : restaurantEntity.getHasRating().getRating());
        restaurantDTO.setIsCreditCardAccepted(restaurantEntity.getAcceptsCreditCard() == null ? null : restaurantEntity.getAcceptsCreditCard().isCreditCardAccepted());
        restaurantDTO.setIsOpen(restaurantEntity.isOpen());
        restaurantDTO.setIsOutdoorSeatingAvailable(restaurantEntity.getHasOutdoorSeating() == null ? null : restaurantEntity.getHasOutdoorSeating().isOutdoorSeatingAvailable());
        return restaurantDTO;
    }
    public static RestaurantUserDTO convertToUserDTO(RestaurantEntity restaurantEntity, Set<Long> likedRestaurants, Set<Long> dislikedRestaurants, Double cosineSimilarity) {
        RestaurantUserDTO restaurantDTO = new RestaurantUserDTO();
        restaurantDTO.setId(restaurantEntity.getId());
        restaurantDTO.setName(restaurantEntity.getName());
        restaurantDTO.setAddress(restaurantEntity.getAddress());
        restaurantDTO.setBusinessId(restaurantEntity.getBusinessId());
        restaurantDTO.setLatitude(restaurantEntity.getLatitude());
        restaurantDTO.setLongitude(restaurantEntity.getLongitude());
        restaurantDTO.setReviewCount(restaurantEntity.getReviewCount());
        if(restaurantEntity.getHoursMon()!=null) restaurantDTO.getHours().put("Monday", restaurantEntity.getHoursMon());
        if(restaurantEntity.getHoursTue()!=null) restaurantDTO.getHours().put("Tuesday", restaurantEntity.getHoursTue());
        if(restaurantEntity.getHoursWed()!=null) restaurantDTO.getHours().put("Wednesday", restaurantEntity.getHoursWed());
        if(restaurantEntity.getHoursThu()!=null) restaurantDTO.getHours().put("Thursday", restaurantEntity.getHoursThu());
        if(restaurantEntity.getHoursFri()!=null) restaurantDTO.getHours().put("Friday", restaurantEntity.getHoursFri());
        if(restaurantEntity.getHoursSat()!=null) restaurantDTO.getHours().put("Saturday", restaurantEntity.getHoursSat());
        if(restaurantEntity.getHoursSun()!=null) restaurantDTO.getHours().put("Sunday", restaurantEntity.getHoursSun());
        restaurantDTO.setCuisines(restaurantEntity.getHasCuisines().stream().map(CuisineMapper::convert).collect(Collectors.toList()));
        restaurantDTO.setAmbiences(restaurantEntity.getHasAmbiences().stream().map(AmbienceMapper::convert).collect(Collectors.toList()));
        restaurantDTO.setIsAlcoholServed(restaurantEntity.getHasAlcohol().isAlcoholServed());
        restaurantDTO.setWifi( restaurantEntity.getHasWifi() == null ? null : restaurantEntity.getHasWifi().getType());
        restaurantDTO.setRating(restaurantEntity.getHasRating() == null ? null : restaurantEntity.getHasRating().getRating());
        restaurantDTO.setIsCreditCardAccepted(restaurantEntity.getAcceptsCreditCard() == null ? null : restaurantEntity.getAcceptsCreditCard().isCreditCardAccepted());
        restaurantDTO.setIsOpen(restaurantEntity.isOpen());
        restaurantDTO.setIsOutdoorSeatingAvailable(restaurantEntity.getHasOutdoorSeating() == null ? null : restaurantEntity.getHasOutdoorSeating().isOutdoorSeatingAvailable());
        // TODO : add liked and disliked
        restaurantDTO.setCosineSimilarity(cosineSimilarity);
        return restaurantDTO;
    }
}
