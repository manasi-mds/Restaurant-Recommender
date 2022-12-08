package com.dbproject.restaurantrecommender.utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DistanceUtil {
    private static final String API_KEY = "AIzaSyBJPrEYIojDH_794kMGjcDrTzihHa6HvwY";
    public static float[][] distances;
    public static float[][] times;

    private static String getData(String source, String destination) throws Exception {
        var url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + source + "&destinations=" + destination + "&key=" + API_KEY;
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private static Long parse(String response){
        long distance = -1L;
        long time = -1L;
        {
            try {
                JSONParser jp = new JSONParser();
                JSONObject jo = (JSONObject) jp.parse(response);
                JSONArray ja = (JSONArray) jo.get("rows");
                jo = (JSONObject) ja.get(0);
                ja = (JSONArray) jo.get("elements");
                jo = (JSONObject) ja.get(0);
                JSONObject je = (JSONObject) jo.get("distance");
                JSONObject jf = (JSONObject) jo.get("duration");
                distance = (long) je.get("value");
                time = (long) jf.get("value");
                return distance;

            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
    }

    public static Double getDistanceInMiles(String source, String destination) throws Exception {
        String data = getData(source, destination);
        if(data==null)
            return null;

        Long distance = parse(data);
        if(distance==null)
            return null;

        return distance * 0.000621371;
    }


    public static double directDistanceInMiles(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
