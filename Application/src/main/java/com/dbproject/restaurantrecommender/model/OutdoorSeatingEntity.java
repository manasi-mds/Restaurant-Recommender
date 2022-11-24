package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("Outdoor_Seating")
public class OutdoorSeatingEntity extends BaseEntity{
    //@Id
    String name;

    public boolean isOutdoorSeatingAvailable(){
        return this.name.equalsIgnoreCase("true");
    }
}
