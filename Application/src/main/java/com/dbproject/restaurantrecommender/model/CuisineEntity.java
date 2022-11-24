package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Cuisine")
@Data
public class CuisineEntity extends BaseEntity{
    //@Id
    String name;
}
