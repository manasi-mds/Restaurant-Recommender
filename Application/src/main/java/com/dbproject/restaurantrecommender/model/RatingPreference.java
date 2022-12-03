package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;


@RelationshipProperties
@Getter
@Setter
public class RatingPreference extends BaseEntity{

    Integer weight;

    @TargetNode
    private RatingEntity ratingEntity;
}
