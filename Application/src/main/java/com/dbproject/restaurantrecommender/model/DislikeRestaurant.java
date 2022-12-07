package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Setter
@Getter
@RelationshipProperties
public class DislikeRestaurant extends BaseEntity{

    @TargetNode
    RestaurantEntity restaurantEntity;
}
