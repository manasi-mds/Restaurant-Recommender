package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@Node("Outdoor_Seating")
public class OutdoorSeatingEntity extends BaseEntity{
    String name;

    public boolean isOutdoorSeatingAvailable(){
        return this.name.equalsIgnoreCase("true");
    }
}
