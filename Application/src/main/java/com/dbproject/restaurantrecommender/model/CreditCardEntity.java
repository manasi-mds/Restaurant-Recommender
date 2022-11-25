package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Credit_Card")
@Data
public class CreditCardEntity extends BaseEntity{
    String name;

    private boolean isCreditCardAccepted(){
        return this.name.equalsIgnoreCase("yes");
    }
}
