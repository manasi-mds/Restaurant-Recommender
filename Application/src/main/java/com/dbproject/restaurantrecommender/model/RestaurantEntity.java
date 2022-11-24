package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.time.LocalDate;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Restaurant")
@Data
public class RestaurantEntity extends BaseEntity{
    String name;
    String address;

    //@Id
    @Property("business_id")
    String businessId;

    Double latitude;
    Double longitude;

    @Property("review_count")
    Integer reviewCount;

    @Property("hours_mon")
    String hoursMon;
    @Property("hours_tue")
    String hoursTue;
    @Property("hours_wed")
    String hoursWed;
    @Property("hours_thu")
    String hoursThu;
    @Property("hours_fri")
    String hoursFri;
    @Property("hours_sat")
    String hoursSat;
    @Property("hours_sun")
    String hoursSun;

    public boolean isOpen(){

        LocalDate currentDate = LocalDate.now();

        String[] time;
        switch(currentDate.getDayOfWeek().getValue()) {

            case 1:
                //Monday
                if(hoursMon.isBlank())
                    return false;
                time = hoursMon.split("-");
                break;
            case 2:
                //Tuesday
                if(hoursTue.isBlank())
                    return false;
                time = hoursTue.split("-");
                break;
            case 3:
                //Wednesday
                if(hoursWed.isBlank())
                    return false;
                time = hoursWed.split("-");
                break;
            case 4:
                //Thursday
                if(hoursThu.isBlank())
                    return false;
                time = hoursThu.split("-");
                break;
            case 5:
                //Friday
                if(hoursFri.isBlank())
                    return false;
                time = hoursFri.split("-");
                break;
            case 6:
                //Saturday
                if(hoursSat.isBlank())
                    return false;
                time = hoursSat.split("-");
                break;
            case 7:
                //Sunday;
                if(hoursSun.isBlank())
                    return false;
                time = hoursSun.split("-");
                break;

            default:
                return false;
        }
        LocalTime curr_time = LocalTime.now();

        LocalTime start_time = LocalTime.parse(time[0]);
        LocalTime end_time = LocalTime.parse(time[1]);

        if( curr_time.compareTo(start_time)<0)
            return false;
        return curr_time.compareTo(end_time) <= 0;
    }

    @Relationship(type = "HAS_CUISINE", direction = OUTGOING)
    Set<CuisineEntity> hasCuisines = new HashSet<>();

    @Relationship(type = "ACCEPTS_CREDIT_CARDS", direction = OUTGOING)
    CreditCardEntity acceptsCreditCard;

    @Relationship(type = "HAS_ALCOHOL", direction = OUTGOING)
    AlcoholEntity hasAlcohol;

    @Relationship(type = "HAS_AMBIENCE", direction = OUTGOING)
    Set<AmbienceEntity> hasAmbiences = new HashSet<>();

    @Relationship(type = "HAS_WIFI", direction = OUTGOING)
    WifiEntity hasWifi;

    @Relationship(type = "HAS_RATING", direction = OUTGOING)
    StarsEntity hasRating;
}
