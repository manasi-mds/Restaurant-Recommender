package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
public class CreditCardPreference extends BaseEntity{
    Integer weight;

    @TargetNode
    CreditCardEntity creditCardEntity;
}
