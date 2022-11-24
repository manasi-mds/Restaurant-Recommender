package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Restaurant")
@Data
public class RestaurantEntity extends BaseEntity{
    String name;
    String address;

    @Property("business_id")
    String businessId;

    Double latitude;
    Double longitude;

    @Property("review_count")
    Integer reviewCount;

    @Property("hours_mon")
    String hoursMon;
    @Property("hours_tue")
    String hoursTue;
    @Property("hours_wed")
    String hoursWed;
    @Property("hours_thu")
    String hoursThu;
    @Property("hours_fri")
    String hoursFri;
    @Property("hours_sat")
    String hoursSat;
    @Property("hours_sun")
    String hoursSun;

    // TODO: Split hours

    // TODO: Attr Relationships

    @Relationship(type = "HAS_CUISINE", direction = OUTGOING)
    Set<CuisineEntity> hasCuisines = new HashSet<>();

    @Relationship(type = "ACCEPTS_CREDIT_CARDS", direction = OUTGOING)
    CreditCardEntity acceptsCreditCard;

    @Relationship(type = "HAS_ALCOHOL", direction = OUTGOING)
    AlcoholEntity hasAlcohol;

    @Relationship(type = "HAS_AMBIENCE", direction = OUTGOING)
    Set<AmbienceEntity> hasAmbiences = new HashSet<>();

    @Relationship(type = "HAS_WIFI", direction = OUTGOING)
    WifiEntity hasWifi;

    @Relationship(type = "HAS_RATING", direction = OUTGOING)
    StarsEntity hasRating;
}
