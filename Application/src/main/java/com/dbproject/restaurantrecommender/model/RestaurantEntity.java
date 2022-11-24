package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Movie")
@Data
public class RestaurantEntity {
    Integer id;
    String name;
    String address;

    @Property("business_id")
    String businessId;
    Double latitude;
    Double longitude;

    @Property("review_count")
    Integer reviewCount;

}
