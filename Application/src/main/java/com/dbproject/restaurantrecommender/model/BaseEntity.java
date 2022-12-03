package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue
    Long id;
}
