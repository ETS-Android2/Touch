package com.seeds.touch.Technical;

import android.location.Location;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LocationDeserializer implements JsonDeserializer<Location> {
    public Location deserialize(JsonElement je, Type type,
                                JsonDeserializationContext jdc)
            throws JsonParseException {
        JsonObject jo = je.getAsJsonObject();
        Location l = new Location(jo.getAsJsonPrimitive("mProvider").getAsString());
        l.setLatitude(jo.getAsJsonPrimitive("mLatitude").getAsFloat());
        l.setLongitude(jo.getAsJsonPrimitive("mLongitude").getAsFloat());
        // etc, getting and setting all the data
        return l;
    }
}