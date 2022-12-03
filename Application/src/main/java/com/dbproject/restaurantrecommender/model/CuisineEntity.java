package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Cuisine")
@Getter
@Setter
public class CuisineEntity extends BaseEntity{
    String name;
}
