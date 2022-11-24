package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@Data
public class BaseEntity {
    @GeneratedValue
    String id;
}
