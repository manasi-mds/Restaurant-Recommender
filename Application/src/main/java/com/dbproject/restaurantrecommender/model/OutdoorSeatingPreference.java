package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class OutdoorSeatingPreference {
    Integer weight;

    @TargetNode
    private OutdoorSeatingEntity outdoorSeatingEntity;
}
