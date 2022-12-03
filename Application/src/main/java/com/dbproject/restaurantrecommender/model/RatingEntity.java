package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Stars")
@Getter
@Setter
public class RatingEntity extends BaseEntity {
    @Property("name")
    Double rating;
}
