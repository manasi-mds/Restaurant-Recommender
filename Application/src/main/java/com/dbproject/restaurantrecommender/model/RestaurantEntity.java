package com.dbproject.restaurantrecommender.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.Date;
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

        LocalDate currentdate = LocalDate.now();

        String[] time;
        switch(currentdate.getDayOfWeek().getValue()) {


            case 1:
                //Monday
                if(hoursMon=="")
                    return false;
                time = hoursMon.split("-");
                break;
            case 2:
                //Tuesday
                if(hoursTue=="")
                    return false;
                time = hoursTue.split("-");
                break;
            case 3:
                //Wednesday
                if(hoursWed=="")
                    return false;
                time = hoursWed.split("-");
                break;
            case 4:
                //Thursday
                if(hoursThu=="")
                    return false;
                time = hoursThu.split("-");
                break;
            case 5:
                //Friday
                if(hoursFri=="")
                    return false;
                time = hoursFri.split("-");
                break;
            case 6:
                //Saturday
                if(hoursSat=="")
                    return false;
                time = hoursSat.split("-");
                break;
            case 7:
                //Sunday;
                if(hoursSun=="")
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
        if(curr_time.compareTo(end_time)>0)
            return false;

        return true;


        // mon-sat?
        // check if the time is present, if null return false

        // break time (from and to) and check if it lies in between
        //LocalTime localTime = LocalTime.parse("04:30");


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
