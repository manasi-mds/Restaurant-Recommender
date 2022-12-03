package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@RelationshipProperties
public class OutdoorSeatingPreference extends BaseEntity{
    Integer weight;

    @TargetNode
    private OutdoorSeatingEntity outdoorSeatingEntity;
}
