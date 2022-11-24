package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

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

}
