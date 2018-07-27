package com.seeds.touch.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.seeds.touch.Activity.ShowItemFullInformationActivity;
import com.seeds.touch.Activity.ShowOthersProfileInformationActivity;
import com.seeds.touch.Configuration.Converter;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Entity.Events.CinemaEvent;
import com.seeds.touch.Entity.Events.RestaurantEvent;
import com.seeds.touch.Entity.Events.TripEvent;
import com.seeds.touch.Listeners.ItemClickSupport;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.Management.Interface.WorldItemAPI;
import com.seeds.touch.Management.Manager.ASyncProfessionalClass;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Server.ServiceGenerator3;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;
import com.seeds.touch.Technical.LocationDeserializer;
import com.seeds.touch.Technical.LocationSerializer;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class F2_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ItemClickSupport listener;
    private List<Item> items;
    private Context context;
    private RecyclerView recyclerView;
    boolean isLoading = false, isMoreDataAvailable = true;
    F2_Adapter.OnLoadMoreListener loadMoreListener;
    public final int TYPE_ITEM = 0;
    public final int TYPE_LOAD = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_ITEM:
                return new F2_Adapter.ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment2_item_layout, parent, false));
            case TYPE_LOAD:
                return new F2_Adapter.ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load_world, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        switch (holder.getItemViewType()) {
            case TYPE_ITEM:
                F2_Adapter.ViewHolder0 holder0 = (F2_Adapter.ViewHolder0) holder;
                handleBindViewHolder(holder0, position);
                break;
            case TYPE_LOAD:
                //
                //no need any binding for loading holder
                break;
        }
    }

    private void handleBindViewHolder(F2_Adapter.ViewHolder0 holder, int position) {

        CardView cardView = (CardView) holder.view.findViewById(R.id.world_card_view);
        ImageView image = (ImageView) holder.view.findViewById(R.id.world_item_image);
        ImageView sticker = (ImageView) holder.view.findViewById(R.id.world_item_sticker);
        TextView stickerLabel = (TextView) holder.view.findViewById(R.id.world_sticker_label);
        CircleImageView publisherPhoto = (CircleImageView) holder.view.findViewById(R.id.world_item_publisher_profile_photo);
        TextView publisherName = (TextView) holder.view.findViewById(R.id.world_item_publisher_name);
        TextView startDate = (TextView) holder.view.findViewById(R.id.world_item_start_date);
        TextView category = (TextView) holder.view.findViewById(R.id.world_item_category);
        TextView population = (TextView) holder.view.findViewById(R.id.world_item_attender_population_text);

        Item item = items.get(position);
        // profile photo listener
        publisherPhoto.setOnClickListener(v -> {
            Intent intent=new Intent(holder.view.getContext(), ShowOthersProfileInformationActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("ID",items.get(position).getPublisher());
            intent.putExtras(bundle);
            holder.view.getContext().startActivity(intent);
        });
        //set publisher name
        if (item.getPublisher() != null) {
            Call<Person> call=ServiceGenerator3.createService(ProfileAPI.class).getProfile(item.getPublisher());
            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    publisherName.setText(response.body().getName()+" "+response.body().getLastName());
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {

                }
            });
        }
        //set population
        if (item.getEvent(item.getEventKey()) != null) {
            population.setText("Attenders : " + items.get(position).getAttenders().size() + " / " + item.getEvent(item.getEventKey()).getATTENDER_NUMBER_RANGE());


        }//set image
        image.setImageResource(R.drawable.mytestimage);
        //set publisher image
        Picasso.with(holder.view.getContext()).load(R.drawable.testimagetwo).resize(90, 90)
                .transform(new CropCircleTransformation()).into(publisherPhoto);
        //set category desc
        String sentence = "";
        if (item.getEvent(item.getEventKey()) instanceof CinemaEvent) {
            sentence = "Watch " + ((CinemaEvent) item.getEvent(item.getEventKey())).getFilmName();
        } else if (item.getEvent(item.getEventKey()) instanceof RestaurantEvent) {
            sentence = "Serve for " + Converter.toCamelCase(((RestaurantEvent) item.getEvent(item.getEventKey())).getMealMode().toString());
        } else if (item.getEvent(item.getEventKey()) instanceof TripEvent) {
            sentence = "Trip to " + item.getEvent(item.getEventKey()).getLocation();
        }
        category.setText(sentence);
        //set stickerLabel
        if (item.getEvent(item.getEventKey()) != null)
            if (items.get(position).getAttenders().contains(Helper.userID))
                stickerLabel.setText("Dismiss");
            else
                stickerLabel.setText("Join");
        //set endDate
        if (item.getEvent(item.getEventKey()) != null)
            startDate.setText(Converter.getDifferenceBetweenCalendars(Calendar.getInstance(), item.getEvent(item.getEventKey()).getStartDate()));
        //
        listener.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                openInSummaryMode(v, items.get(position));
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {
                //////////////////////////////////////////////////
                Call<Item> call = ServiceGenerator.createService(WorldItemAPI.class).
                        getItem(Integer.parseInt(items.get(position).getDatabaseID()));
                call.enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        Item item = response.body();
                        Log.d("DSFsX", GSON_Wrapper.getInstance().toJson(item));
                        HashSet<String> hashSet = item.getAttenders();
                        if (hashSet.contains(Helper.userID)) {
                            hashSet.remove(Helper.userID);
                            Map<String, String> map = new HashMap<>();
                            map.put("databaseID", item.getDatabaseID());
                            map.put("Attenders", GSON_Wrapper.getInstance().toJson(hashSet));
                            Call<Integer> call2 = ServiceGenerator.createService(WorldItemAPI.class).updateItem(map);
                            call2.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    switch (Enums.UpdateItemResult.values()[response.body()]) {
                                        case SUCCESSFUL:
                                            Toast.makeText(v.getContext(), "update item for dismissing done !", Toast.LENGTH_LONG).show();
                                            Call<Item> itemCall = ServiceGenerator.createService(WorldItemAPI.class).getItem(Integer.parseInt(item.getDatabaseID()));
                                            itemCall.enqueue(new Callback<Item>() {
                                                @Override
                                                public void onResponse(Call<Item> call, Response<Item> response) {
                                                    items.get(position).setJSONAttenders(GSON_Wrapper.getInstance().toJson(response.body().getAttenders()));
                                                    notifyDataChanged();
                                                }

                                                @Override
                                                public void onFailure(Call<Item> call, Throwable t) {
                                                    Log.d("GHV", "DSFDB");
                                                }
                                            });

                                            break;
                                        case FAILED:

                                            Toast.makeText(v.getContext(), "Failed to update item information", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Log.d("ASDAS", "ERRROR!");
                                }
                            });

                        } else {

                            HashSet<String> hashSet2 = item.getAttenders();
                            hashSet2.add(Helper.userID);
                            Log.d("DFSX", Helper.userID + "   " + GSON_Wrapper.getInstance().toJson(hashSet2));
                            Map<String, String> map = new HashMap<>();
                            map.put("databaseID", item.getDatabaseID());
                            map.put("Attenders", GSON_Wrapper.getInstance().toJson(hashSet2));
                            Call<Integer> call3 = ServiceGenerator.createService(WorldItemAPI.class).updateItem(map);
                            call3.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    switch (Enums.UpdateItemResult.values()[response.body()]) {
                                        case SUCCESSFUL:
                                            Toast.makeText(v.getContext(), "update item for joining done !", Toast.LENGTH_LONG).show();

                                            Call<Item> itemCall = ServiceGenerator.createService(WorldItemAPI.class).getItem(Integer.parseInt(item.getDatabaseID()));
                                            itemCall.enqueue(new Callback<Item>() {
                                                @Override
                                                public void onResponse(Call<Item> call, Response<Item> response) {
                                                    items.get(position).setJSONAttenders(GSON_Wrapper.getInstance().toJson(response.body().getAttenders()));
                                                    notifyDataChanged();
                                                }

                                                @Override
                                                public void onFailure(Call<Item> call, Throwable t) {
                                                    Log.d("GHJ", "SDS");
                                                }
                                            });
                                            break;
                                        case FAILED:

                                            Toast.makeText(v.getContext(), "Failed to update item information", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Log.d("ASDAS", "ERRROR!");
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        Log.d("FGHV", "DFDFSDCEEEEE");
                    }
                });
                //////////////////////////////////////////////////
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isLoadItem() ? TYPE_LOAD : TYPE_ITEM;
    }

    private void openInSummaryMode(View v, Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.summary_of_event, null);
        builder.setView(dialogView);
        Button button = (Button) dialogView.findViewById(R.id.test_btn);
        TextView tv = (TextView) dialogView.findViewById(R.id.test_tv);
        tv.setText(item.getPublisher());
        button.setOnClickListener(v1 -> {
            Intent intent = new Intent(v.getContext(), ShowItemFullInformationActivity.class);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Location.class, new LocationDeserializer());
            gsonBuilder.registerTypeAdapter(Location.class, new LocationSerializer());
            Gson gson = gsonBuilder.create();
            intent.putExtra("Item", GSON_Wrapper.getInstance().toJson(item));
            String type = item.getEvent(item.getEventKey()) instanceof CinemaEvent ? "cinema" : (item.getEvent(item.getEventKey()) instanceof TripEvent ? "trip" : "restaurant");
            intent.putExtra("Type", GSON_Wrapper.getInstance().toJson(type));
            v.getContext().startActivity(intent);
        });
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //for item
    public static class ViewHolder0 extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder0(View v) {
            super(v);
            view = v;
        }
    }

    //for load item
    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder1(View v) {
            super(v);
            view = v;
        }
    }

    public F2_Adapter(List<Item> items, Context context, RecyclerView recyclerView) {
        this.items = items;
        this.context = context;
        this.listener = ItemClickSupport.addTo(context, recyclerView);

    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(F2_Adapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
