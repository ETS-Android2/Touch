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
import com.seeds.touch.Activity.AddItemActivity;
import com.seeds.touch.Activity.ShowItemFullInformationActivity;
import com.seeds.touch.Configuration.Converter;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Entity.Events.CinemaEvent;
import com.seeds.touch.Entity.Events.RestaurantEvent;
import com.seeds.touch.Entity.Events.TripEvent;
import com.seeds.touch.Listeners.ItemClickSupport;
import com.seeds.touch.Management.Interface.HomeItemAPI;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.Management.Interface.WorldItemAPI;
import com.seeds.touch.Management.Manager.ASyncProfessionalClass;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Server.ServiceGenerator;
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

public class F1_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ItemClickSupport listener;
    private List<Item> items;
    private Context context;
    private RecyclerView recyclerView;
    public final int TYPE_ADD_ITEM = 0;
    public final int TYPE_ITEM = 1;
    public final int TYPE_LOAD = 2;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    //for add item
    public static class ViewHolder0 extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder0(View v) {
            super(v);
            view = v;
        }
    }

    //for item
    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder1(View v) {
            super(v);
            view = v;
        }
    }

    //for load
    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder2(View v) {
            super(v);
            view = v;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_ADD_ITEM:
                return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment1_add_item_layout, parent, false));
            case TYPE_ITEM:
                return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment1_item_layout, parent, false));
            case TYPE_LOAD:
                return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_load, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_ADD_ITEM : (items.get(position).isLoadItem() ? TYPE_LOAD : TYPE_ITEM);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        switch (holder.getItemViewType()) {
            case TYPE_ADD_ITEM:
                ViewHolder0 holder0 = (ViewHolder0) holder;
                handleBindViewHolder(holder0, position);
                break;
            case TYPE_ITEM:
                ViewHolder1 holder1 = (ViewHolder1) holder;
                handleBindViewHolder(holder1, position);
                break;
            case TYPE_LOAD:
                //
                //no need any binding for loading holder
                break;
        }
    }

    private void handleBindViewHolder(ViewHolder0 holder, int position) {
        CircleImageView publisherPhoto = holder.view.findViewById(R.id.home_add_item_item_publisher_profile_photo);
        TextView publisherName = holder.view.findViewById(R.id.home_add_item_item_publisher_name);
        TextView category = holder.view.findViewById(R.id.home_add_item_item_category);

        category.setText("What do you want to do ? ");
        publisherName.setText("You");
        publisherPhoto.setImageResource(R.drawable.mytestimage);

        listener.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                openAddItemDialog(v);
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {
                //do nothing
            }
        });
        Log.d("WERT", "" + position);
    }

    private void handleBindViewHolder(ViewHolder1 holder, int position) {
        CardView cardView = holder.view.findViewById(R.id.home_card_view);
        ImageView image = holder.view.findViewById(R.id.home_item_image);
        ImageView sticker = holder.view.findViewById(R.id.home_item_sticker);
        TextView stickerLabel = holder.view.findViewById(R.id.home_sticker_label);
        CircleImageView publisherPhoto = holder.view.findViewById(R.id.home_item_publisher_profile_photo);
        TextView publisherName = holder.view.findViewById(R.id.home_item_publisher_name);
        TextView category = holder.view.findViewById(R.id.home_item_category);
        TextView startDate = holder.view.findViewById(R.id.home_item_start_date);
        TextView population = holder.view.findViewById(R.id.home_item_attender_population_text);


        //this listener is for both layouts(no way :(  , i tried )
        listener.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                //Log.d("OPOI", "" + position + items.get(position));
                //if (position == 0 && items.get(position) == null)
                //     Toast.makeText(v.getContext(), "ADDING", Toast.LENGTH_LONG).show();
                // else
                switch (position) {
                    case 0:
                        openAddItemDialog(v);
                        break;
                    case TYPE_ITEM:
                        openInSummaryMode(v, items.get(position));
                        break;
                }
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {
                switch (getItemViewType(position)) {
                    case TYPE_ADD_ITEM:
                        Toast.makeText(v.getContext(), "Double click detected ...", Toast.LENGTH_LONG).show();
                        break;
                    case TYPE_ITEM:
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
                        break;
                }
            }
        });
        //if (position == 0 && items.get(position) == null) {

        //} else {
        //set publisher name
        if (items.get(position) != null && items.get(position).getPublisher() != null) {
            Call<Person> call=ServiceGenerator.createService(ProfileAPI.class).getProfile(items.get(position).getPublisher());
            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    String name = items.get(position).getPublisher();
                    publisherName.setText(name);
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.d("SDFX","ERRROROROROROR");
                }
            });
        }
        //set population
        if (items.get(position) != null && items.get(position).getEvent(items.get(position).getEventKey()) != null && items.get(position).getAttenders() != null) {
            population.setText("Attenders : " + items.get(position).getAttenders().size() + " / " + items.get(position).getEvent(items.get(position).getEventKey()).getATTENDER_NUMBER_RANGE());
        }
        //set image
        image.setImageResource(R.drawable.mytestimage);
        //set publisher image
        Picasso.with(holder.view.getContext()).load(R.drawable.testimagetwo).resize(90, 90)
                .transform(new CropCircleTransformation()).into(publisherPhoto);
        //set category desc
        if (items.get(position) != null && items.get(position).getEvent(items.get(position).getEventKey()) != null) {
            String sentence = "";
            Item item = items.get(position);
            if (item.getEvent(item.getEventKey()) instanceof CinemaEvent) {
                sentence = "Watch " + ((CinemaEvent) item.getEvent(item.getEventKey())).getFilmName();
            } else if (item.getEvent(item.getEventKey()) instanceof RestaurantEvent) {
                sentence = "Serve for " + Converter.toCamelCase(((RestaurantEvent) item.getEvent(item.getEventKey())).getMealMode().toString());
            } else if (item.getEvent(item.getEventKey()) instanceof TripEvent) {
                sentence = "Trip to " + item.getEvent(item.getEventKey()).getLocation();
            }
            category.setText(sentence);
        }
        //set stickerLabel
        if (items.get(position) != null && items.get(position).getEvent(items.get(position).getEventKey()) != null) {
            if (items.get(position).getAttenders().contains(Helper.userID))
                stickerLabel.setText("Dismiss");
            else
                stickerLabel.setText("Join");
        }
        //set endDate
        if (items.get(position) != null && items.get(position).getEvent(items.get(position).getEventKey()) != null)
            startDate.setText(Converter.getDifferenceBetweenCalendars(Calendar.getInstance(), items.get(position).getEvent(items.get(position).getEventKey()).getStartDate()));
        //
        //}

    }

    private void openAddItemDialog(View v) {
        AlertDialog alertDialog1;
        final CharSequence[] options = new String[]{"Cinema", "Restaurant", "Trip"};
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Which type ?");
        builder.setCancelable(true);
        builder.setSingleChoiceItems(options, -1, (dialog, item) -> {
            Intent intent = new Intent(v.getContext(), AddItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Type", options[item].toString());
            intent.putExtras(bundle);
            ((Activity) v.getContext()).startActivity(intent);
            dialog.dismiss();
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
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


    public F1_Adapter(List<Item> items, Context context, RecyclerView recyclerView) {
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

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    private void Toggle(TextView tv) {
        if (tv.getText().equals("Dismiss"))
            tv.setText("join");
        tv.setText("Dismiss");

    }
}
