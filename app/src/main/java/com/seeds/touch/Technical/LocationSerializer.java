package com.seeds.touch.Technical;

import android.location.Location;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class LocationSerializer
        implements JsonSerializer<Location> {
    public JsonElement serialize(Location t, Type type,
                                 JsonSerializationContext jsc) {
        JsonObject jo = new JsonObject();
        jo.addProperty("mProvider", t.getProvider());
        jo.addProperty("mLatitude", t.getLatitude());
        jo.addProperty("mLongitude", t.getLongitude());
        // etc for all the publicly available getters
        // for the information you're interested in
        // ...
        return jo;
    }

}