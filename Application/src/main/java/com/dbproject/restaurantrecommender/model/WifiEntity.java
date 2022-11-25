package com.dbproject.restaurantrecommender.model;

import com.dbproject.restaurantrecommender.enums.WifiType;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Wifi")
@Data
public class WifiEntity extends BaseEntity {
    WifiType type;
}
