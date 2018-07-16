package com.seeds.touch.Fragment.Fragment1;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seeds.touch.Adapter.F1_Adapter;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Comment;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Entity.Events.CinemaEvent;
import com.seeds.touch.Entity.Events.Event;
import com.seeds.touch.Entity.Events.RestaurantEvent;
import com.seeds.touch.Entity.Events.TripEvent;
import com.seeds.touch.Listeners.ItemClickSupport;
import com.seeds.touch.Management.Interface.PostTimeInterface;
import com.seeds.touch.Management.Manager.ItemManager;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.Helper;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;

public class Fragment1 extends Fragment {
    private LinkedList<Item> items = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        findViews(view);
        tuneRecyclerView(view);
        ItemManager.getInstance().readItems(view.getContext(), Enums.EventTypes.HOME, objects -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2019);


            HashSet<Comment> comments = new HashSet<>();
            comments.add(new Comment("Salam , aali bood", Calendar.getInstance(), "Hasan"));
            comments.add(new Comment("Merciiii", Calendar.getInstance(), "Parvaneh"));

            HashSet<String> attenders = new HashSet<>();
            attenders.add("Reza");
            attenders.add("Baghiat");
            attenders.add("Company");

            HashSet<String> tags = new HashSet<>();
            tags.add("Bagh");
            tags.add("Flowers");
            tags.add("Beauty");

            HashSet<String> pictures = new HashSet<>();
            pictures.add("url1");
            pictures.add("url2");
            pictures.add("url3");

            Location location = new Location("Touch");
            Bundle bundle = new Bundle();
            bundle.putString("NAME", "California");
            location.setExtras(bundle);
            location.setLatitude(2365.12);
            location.setLongitude(7654.70);

            Event event = new CinemaEvent("Watch The Best Film", Calendar.getInstance(), Calendar.getInstance(), location, "Can You Get A One?1", 45, "Ali O Dani1");
            Event event2 = new TripEvent("Watch The Best Film2", Calendar.getInstance(), calendar, location, "Can You Get A One?2", 46);
            Event event3 = new RestaurantEvent("Watch The Best Film3", Calendar.getInstance(), Calendar.getInstance(), location, "Can You Get A One?3", 47, Enums.MealMode.BREAKFAST);
            Event event4 = new CinemaEvent("Watch The Best Film4", Calendar.getInstance(), Calendar.getInstance(), location, "Can You Get A One?4", 48, "Ali O Dani4");

            Item item = new Item("10", pictures, Calendar.getInstance(), tags, event, "Mohammad", attenders, comments, 12, Enums.Status.SHOWN, Enums.AccessType.PUBLIC);
            Item item2 = new Item("2", pictures, Calendar.getInstance(), tags, event2, "Simin", attenders, comments, 10, Enums.Status.SHOWN, Enums.AccessType.PUBLIC);
            Item item3 = new Item("3", pictures, Calendar.getInstance(), tags, event3, "Shirin", attenders, comments, 65, Enums.Status.SHOWN, Enums.AccessType.PUBLIC);
            Item item4 = new Item("4", pictures, Calendar.getInstance(), tags, event4, "Nafas", attenders, comments, 0, Enums.Status.SHOWN, Enums.AccessType.FRIENDS);
            items.add(item);// = (LinkedList<Item>) objects[0];
            items.add(item2);// = (LinkedList<Item>) objects[0];
            items.add(item3);// = (LinkedList<Item>) objects[0];
            items.add(item4);// = (LinkedList<Item>) objects[0];
            Fragment1.updateList();
        });

        return view;
    }

    private static void updateList() {
        Helper.fragment1_RecyclerView.setAdapter(Helper.fragment1_Adapter);
        Helper.fragment1_Adapter.notifyDataSetChanged();
    }

    private void tuneRecyclerView(View view) {
        Helper.fragment1_RecyclerView.setHasFixedSize(true);
        Helper.fragment1_LinearLayoutManager = new LinearLayoutManager(getContext());
        Helper.fragment1_RecyclerView.setLayoutManager(Helper.fragment1_LinearLayoutManager);
        Helper.fragment1_Adapter = new F1_Adapter(items, view.getContext(),Helper.fragment1_RecyclerView);
    }

    private void findViews(View view) {
        Helper.fragment1_RecyclerView = (RecyclerView) view.findViewById(R.id.fragment1_recyclerview);
    }
}
