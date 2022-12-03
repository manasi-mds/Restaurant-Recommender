package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
public class AmbiencePreference extends BaseEntity{
    Integer weight;

    @TargetNode
    @ToString.Exclude
    private AmbienceEntity ambienceEntity;
}
