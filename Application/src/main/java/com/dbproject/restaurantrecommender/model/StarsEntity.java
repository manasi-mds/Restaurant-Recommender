package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Stars")
@Data
public class StarsEntity extends BaseEntity {
    //@Id
    @Property("name")
    Float rating;
}
