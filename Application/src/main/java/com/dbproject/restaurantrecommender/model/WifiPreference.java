package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
public class WifiPreference extends BaseEntity{
    Integer weight;

    @TargetNode
    private WifiEntity wifi;
}
