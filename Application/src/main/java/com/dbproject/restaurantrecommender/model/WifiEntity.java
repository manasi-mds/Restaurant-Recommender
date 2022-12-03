package com.dbproject.restaurantrecommender.model;

import com.dbproject.restaurantrecommender.enums.WifiType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Wifi")
@Getter
@Setter
public class WifiEntity extends BaseEntity {
    WifiType type;
}
