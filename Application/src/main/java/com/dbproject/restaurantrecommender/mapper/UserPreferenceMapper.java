package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;
import com.dbproject.restaurantrecommender.dto.preference.*;
import com.dbproject.restaurantrecommender.model.AmbiencePreference;
import com.dbproject.restaurantrecommender.model.CuisinePreference;
import com.dbproject.restaurantrecommender.model.UserEntity;


public class UserPreferenceMapper {
    public static UserEntity create(UserPreferenceDTO userPreferenceDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userPreferenceDTO.getName());
        userEntity.setEmail(userPreferenceDTO.getEmail());
        userEntity.setPassword(userPreferenceDTO.getPassword());
        return userEntity;

    }
    public static UserPreferenceDTO convert(UserEntity userEntity) {
        UserPreferenceDTO userDTO = new UserPreferenceDTO();
        userDTO.setUserId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());

        AlcoholServedPreferenceDTO alcoholServedPreferenceDTO = new AlcoholServedPreferenceDTO();
        if(userEntity.getAlcoholPreference()!=null)
            alcoholServedPreferenceDTO.setIsAlcoholServed(userEntity.getAlcoholPreference().getAlcoholEntity().isAlcoholServed());
        userDTO.setAlcoholServed(alcoholServedPreferenceDTO);

        for(CuisinePreference up:userEntity.getCuisinePreferences()){
            CuisinePreferenceDTO cuisinePreferenceDTO = new CuisinePreferenceDTO();
            cuisinePreferenceDTO.setCuisineId(up.getCuisineEntity().getId());
            cuisinePreferenceDTO.setCuisineName(up.getCuisineEntity().getName());
            cuisinePreferenceDTO.setWeight(up.getWeight());
            userDTO.getCuisines().add(cuisinePreferenceDTO);
        }

        for(AmbiencePreference ap : userEntity.getAmbiencePreferences()){
            AmbiencePreferenceDTO ambiencePreferenceDTO = new AmbiencePreferenceDTO();
            ambiencePreferenceDTO.setAmbienceId(ap.getAmbienceEntity().getId());
            ambiencePreferenceDTO.setAmbienceName(ap.getAmbienceEntity().getName());
            ambiencePreferenceDTO.setWeight(ap.getWeight());
            userDTO.getAmbiences().add(ambiencePreferenceDTO);
        }

        CreditCardPreferenceDTO creditCardPreferenceDTO = new CreditCardPreferenceDTO();
        if(userEntity.getCreditCardPreference()!=null)
            creditCardPreferenceDTO.setIsCreditCardAccepted(userEntity.getCreditCardPreference().getCreditCardEntity().isCreditCardAccepted());
        userDTO.setCreditCardAccepted(creditCardPreferenceDTO);

        WifiPreferenceDTO wifiPreferenceDTO = new WifiPreferenceDTO();
        if(userEntity.getWifiPreference()!=null)
            wifiPreferenceDTO.setWifiType(userEntity.getWifiPreference().getWifi().getType().toString());
        userDTO.setWifiTypeAvailable(wifiPreferenceDTO);

        RatingPreferenceDTO ratingPreferenceDTO = new RatingPreferenceDTO();
        if(userEntity.getMinimumRating()!=null)
            ratingPreferenceDTO.setMinRating(userEntity.getMinimumRating().getRatingEntity().getRating());
        userDTO.setMinimumRating(ratingPreferenceDTO);

        OutdoorSeatingPreferenceDTO outdoorSeatingPreferenceDTO = new OutdoorSeatingPreferenceDTO();
        if(userEntity.getOutdoorSeatingPreference()!=null)
            outdoorSeatingPreferenceDTO.setIsOutdoorSeatingAvailable(userEntity.getOutdoorSeatingPreference().getOutdoorSeatingEntity().isOutdoorSeatingAvailable());
        userDTO.setOutdoorSeating(outdoorSeatingPreferenceDTO);

        userDTO.setDistance(userEntity.getDistancePreference());
        return userDTO;
    }
}
