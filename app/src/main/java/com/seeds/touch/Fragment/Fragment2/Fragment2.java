package com.seeds.touch.Fragment.Fragment2;

import android.content.Context;
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
import com.seeds.touch.Adapter.F2_Adapter;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Management.Interface.WorldItemAPI;
import com.seeds.touch.R;
import com.seeds.touch.Server.ServiceGenerator2;
import com.seeds.touch.Technical.Helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.seeds.touch.Technical.Helper.TAG;

public class Fragment2 extends Fragment {
    private LinkedList<Item> items = new LinkedList<>();
    private WorldItemAPI api;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        items.clear();
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        findViews(view);
        tuneRecyclerView(view);
        api = ServiceGenerator2.createService(WorldItemAPI.class);


//        ItemManager.getInstance().readItems(view.getContext(), Enums.EventTypes.WORLD, objects -> {
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.YEAR, 2019);
//
//
//            HashSet<Comment> comments = new HashSet<>();
//            comments.add(new Comment("Salam , aali bood", Calendar.getInstance(), "Hasan"));
//            comments.add(new Comment("Merciiii", Calendar.getInstance(), "Parvaneh"));
//
//            HashSet<String> attenders = new HashSet<>();
//            attenders.add("Reza");
//            attenders.add("Baghiat");
//            attenders.add("Company");
//
//            HashSet<String> tags = new HashSet<>();
//            tags.add("Bagh");
//            tags.add("Flowers");
//            tags.add("Beauty");
//
//            HashSet<String> pictures = new HashSet<>();
//            pictures.add("url1");
//            pictures.add("url2");
//            pictures.add("url3");
//
//            Location location = new Location("Touch");
//            Bundle bundle = new Bundle();
//            bundle.putString("NAME", "California");
//            location.setExtras(bundle);
//            location.setLatitude(2365.12);
//            location.setLongitude(7654.70);
//
//            Event event = new CinemaEvent("Watch The Best Film", Calendar.getInstance(), Calendar.getInstance(), location, "Can You Get A One?1", 45, "Ali O Dani1");
//            Event event2 = new TripEvent("Watch The Best Film2", Calendar.getInstance(), calendar, location, "Can You Get A One?2", 46);
//            Event event3 = new RestaurantEvent("Watch The Best Film3", Calendar.getInstance(), Calendar.getInstance(), location, "Can You Get A One?3", 47, Enums.MealMode.BREAKFAST);
//            Event event4 = new CinemaEvent("Watch The Best Film4", Calendar.getInstance(), Calendar.getInstance(), location, "Can You Get A One?4", 48, "Ali O Dani4");
//
//            Item item = new Item("10", pictures, Calendar.getInstance(), tags, event, "Mohammad", attenders, comments, 12, Enums.Status.SHOWN, Enums.AccessType.PUBLIC);
//            Item item2 = new Item("2", pictures, Calendar.getInstance(), tags, event2, "Simin", attenders, comments, 10, Enums.Status.SHOWN, Enums.AccessType.PUBLIC);
//            Item item3 = new Item("3", pictures, Calendar.getInstance(), tags, event3, "Shirin", attenders, comments, 65, Enums.Status.SHOWN, Enums.AccessType.PUBLIC);
//            Item item4 = new Item("4", pictures, Calendar.getInstance(), tags, event4, "Nafas", attenders, comments, 0, Enums.Status.SHOWN, Enums.AccessType.FRIENDS);
//            items.add(item);// = (LinkedList<Item>) objects[0];
//            items.add(item2);// = (LinkedList<Item>) objects[0];
//            items.add(item3);// = (LinkedList<Item>) objects[0];
//            items.add(item4);// = (LinkedList<Item>) objects[0];
//            Fragment2.updateList();
//        });
        load(0);

        return view;
    }

    private void load(int index) {
        Call<Item> call = api.getItems(index);
        Log.d("FGCV",call+"");
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.d("FGHBVNC", new Gson().toJson(response.body()));
                //if (response.isSuccessful()) {
                    items.add(response.body());
                    ((F2_Adapter) Helper.fragment2_Adapter).notifyDataChanged();
               // } else {
               //     Log.e(TAG, " Response Error " + String.valueOf(response.code()));
                //}
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.e(TAG, " Rxcvesponse Error " + t.getMessage());
            }
        });
    }

    private void findViews(View view) {
        Helper.fragment2_RecyclerView = (RecyclerView) view.findViewById(R.id.fragment2_recyclerview);


    }

    private void tuneRecyclerView(View view) {
        Helper.fragment2_LinearLayoutManager = new LinearLayoutManager(getContext());
        Helper.fragment2_RecyclerView.setHasFixedSize(true);
        Helper.fragment2_RecyclerView.setLayoutManager(Helper.fragment2_LinearLayoutManager);
        Helper.fragment2_Adapter = new F2_Adapter(items, view.getContext(), Helper.fragment2_RecyclerView);
        ((F2_Adapter) Helper.fragment2_Adapter).setLoadMoreListener(() -> {

            Helper.fragment2_RecyclerView.post(() -> {
                int index = items.size() ;
                Fragment2.this.loadMore(getContext(), index);// a method which requests remote data
            });
            //Calling loadMore function in Runnable to fix the
            // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
        });
        Helper.fragment2_RecyclerView.setAdapter(Helper.fragment2_Adapter);
    }

    private void loadMore(Context context, int index) {
        Item item = new Item();
        item.setLoadItem(true);
//add loading progress view
        items.add(item);
        Helper.fragment2_Adapter.notifyItemInserted(items.size() - 1);

        Call<Item> call = api.getItems(index);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.d("DFGCB", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {

                    //remove loading view
                    items.remove(items.size() - 1);
                    Item result = response.body();
                    if (result!=null) {
                        //add loaded data
                        items.add(result);
                    } else {//result size 0 means there is no more data available at server
                        ((F2_Adapter) Helper.fragment2_Adapter).setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context, "No More Data Available", Toast.LENGTH_LONG).show();
                    }
                    ((F2_Adapter) Helper.fragment2_Adapter).notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                } else {
                    Log.e(TAG, " Load More Response Error " + String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.e(TAG, " Load More Response Error " + t.getMessage());
            }
        });
    }
}