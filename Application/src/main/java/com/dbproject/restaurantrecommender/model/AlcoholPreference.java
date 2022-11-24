package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class AlcoholPreference extends BaseEntity{

    Integer weight;

    @TargetNode
    AlcoholEntity alcoholEntity;
}
