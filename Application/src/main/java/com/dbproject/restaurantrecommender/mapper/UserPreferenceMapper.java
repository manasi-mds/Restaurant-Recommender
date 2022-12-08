package com.dbproject.restaurantrecommender.mapper;

import com.dbproject.restaurantrecommender.dto.UserPreferenceDTO;
import com.dbproject.restaurantrecommender.dto.preference.*;
import com.dbproject.restaurantrecommender.model.AmbiencePreference;
import com.dbproject.restaurantrecommender.model.CuisinePreference;
import com.dbproject.restaurantrecommender.model.UserEntity;

import java.util.ArrayList;
import java.util.List;


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
        System.out.println(userEntity);
        if(userEntity.getAlcoholPreference()!=null) {
            AlcoholServedPreferenceDTO alcoholServedPreferenceDTO = new AlcoholServedPreferenceDTO();
            alcoholServedPreferenceDTO.setIsAlcoholServed(userEntity.getAlcoholPreference().getAlcoholEntity().isAlcoholServed());
            userDTO.setAlcoholServed(alcoholServedPreferenceDTO);
        }

        List<CuisinePreferenceDTO> cuisinePreferenceDTOS = new ArrayList<>();
        for(CuisinePreference up:userEntity.getCuisinePreferences()){
            CuisinePreferenceDTO cuisinePreferenceDTO = new CuisinePreferenceDTO();
            cuisinePreferenceDTO.setCuisineId(up.getCuisineEntity().getId());
            cuisinePreferenceDTO.setCuisineName(up.getCuisineEntity().getName());
            cuisinePreferenceDTO.setWeight(up.getWeight());
            cuisinePreferenceDTOS.add(cuisinePreferenceDTO);
        }
        userDTO.setCuisines(cuisinePreferenceDTOS);

        List<AmbiencePreferenceDTO> ambiencePreferenceDTOS = new ArrayList<>();
        for(AmbiencePreference ap : userEntity.getAmbiencePreferences()){
            AmbiencePreferenceDTO ambiencePreferenceDTO = new AmbiencePreferenceDTO();
            ambiencePreferenceDTO.setAmbienceId(ap.getAmbienceEntity().getId());
            ambiencePreferenceDTO.setAmbienceName(ap.getAmbienceEntity().getName());
            ambiencePreferenceDTO.setWeight(ap.getWeight());
            ambiencePreferenceDTOS.add(ambiencePreferenceDTO);
        }
        userDTO.setAmbiences(ambiencePreferenceDTOS);

        if(userEntity.getCreditCardPreference()!=null){
            CreditCardPreferenceDTO creditCardPreferenceDTO = new CreditCardPreferenceDTO();
            creditCardPreferenceDTO.setIsCreditCardAccepted(userEntity.getCreditCardPreference().getCreditCardEntity().isCreditCardAccepted());
            userDTO.setCreditCardAccepted(creditCardPreferenceDTO);
        }

        if(userEntity.getWifiPreference()!=null) {
            WifiPreferenceDTO wifiPreferenceDTO = new WifiPreferenceDTO();
            wifiPreferenceDTO.setWifiType(userEntity.getWifiPreference().getWifi().getType().toString());
            userDTO.setWifiTypeAvailable(wifiPreferenceDTO);
        }

        if(userEntity.getMinimumRating()!=null) {
            RatingPreferenceDTO ratingPreferenceDTO = new RatingPreferenceDTO();
            ratingPreferenceDTO.setMinRating(userEntity.getMinimumRating().getRatingEntity().getRating());
            userDTO.setMinimumRating(ratingPreferenceDTO);
        }

        if(userEntity.getOutdoorSeatingPreference()!=null) {
            OutdoorSeatingPreferenceDTO outdoorSeatingPreferenceDTO = new OutdoorSeatingPreferenceDTO();
            outdoorSeatingPreferenceDTO.setIsOutdoorSeatingAvailable(userEntity.getOutdoorSeatingPreference().getOutdoorSeatingEntity().isOutdoorSeatingAvailable());
            userDTO.setOutdoorSeating(outdoorSeatingPreferenceDTO);
        }

        userDTO.setDistance(userEntity.getDistancePreference());
        return userDTO;
    }
}
