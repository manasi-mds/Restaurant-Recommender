package com.dbproject.restaurantrecommender.model;

import org.springframework.data.neo4j.core.schema.TargetNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;


@RelationshipProperties
@Data

public class StarsPreference {

    Integer weight;

    @TargetNode
    private StarsEntity stars;
}
