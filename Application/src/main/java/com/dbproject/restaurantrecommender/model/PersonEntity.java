package com.dbproject.restaurantrecommender.model;

import org.springframework.data.neo4j.core.schema.Node;

@Node("Person")
public class PersonEntity extends BaseEntity{
    String name;
    String email;
    String password;

    // TODO: Last Preferences relationship

    // TODO: Self relationship for friends

    // TODO: Restaurant Relationship - liked


}
