package com.seeds.touch.Technical;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GSON_Wrapper{
    private static Gson instance = null;

    public static Gson getInstance() {

        if(instance ==null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Location.class, new LocationDeserializer());
            gsonBuilder.registerTypeAdapter(Location.class, new LocationSerializer());
            instance =gsonBuilder.create();
        }
        return instance;

    }

    private GSON_Wrapper() {
    }
}
