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


}
