package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.R;
import com.seeds.touch.Technical.LocationDeserializer;
import com.seeds.touch.Technical.LocationSerializer;

public class DetailFragment_Restaurant extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_fragment__restaurant, container, false);
        findViews(view);
        manageListeners(view);
        return view;
    }

    private void manageListeners(View view) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Location.class, new LocationDeserializer());
        gsonBuilder.registerTypeAdapter(Location.class, new LocationSerializer());
        Gson gson = gsonBuilder.create();

        String GSONitem=getArguments().getString("Item");
        Item item=gson.fromJson(GSONitem,Item.class);
        ((TextView)view.findViewById(R.id.sdsadv)).setText(item.getPublisher());
    }

    private void findViews(View view) {
    }
}
