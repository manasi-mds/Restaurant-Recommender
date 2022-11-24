package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("Outdoor_Seating")
public class OutdoorSeatingEntity extends BaseEntity{
    String name;

    public boolean isOutdoorSeatingAvailable(){
        return this.name.equalsIgnoreCase("true");
    }
}
