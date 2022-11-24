package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Alcohol")
@Data
public class AlcoholEntity extends BaseEntity {
    String name;
    public boolean isAlcoholServed(){
        return this.name.equalsIgnoreCase("yes");
    }
}
