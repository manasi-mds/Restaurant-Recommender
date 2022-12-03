package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Alcohol")
@Getter
@Setter
public class AlcoholEntity extends BaseEntity {
    String name;

    public boolean isAlcoholServed(){
        return this.name.equalsIgnoreCase("yes");
    }
}
