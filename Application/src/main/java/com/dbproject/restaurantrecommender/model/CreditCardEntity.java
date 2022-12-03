package com.dbproject.restaurantrecommender.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Credit_Card")
@Getter
@Setter
public class CreditCardEntity extends BaseEntity{
    String name;

    public boolean isCreditCardAccepted() {
        return this.name.equalsIgnoreCase("yes");
    }
}
