package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Ambience")
@Getter
@Setter
public class AmbienceEntity extends BaseEntity{
    String name;
}
