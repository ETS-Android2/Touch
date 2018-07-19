package com.seeds.touch.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import com.seeds.touch.Activity.ShowItemFullInformationActivity;
import com.seeds.touch.Configuration.Converter;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Entity.Events.CinemaEvent;
import com.seeds.touch.Entity.Events.RestaurantEvent;
import com.seeds.touch.Entity.Events.TripEvent;
import com.seeds.touch.Listeners.ItemClickSupport;
import com.seeds.touch.Management.Manager.ASyncProfessionalClass;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Technical.Helper;
import com.seeds.touch.Technical.LocationDeserializer;
import com.seeds.touch.Technical.LocationSerializer;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class F2_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ItemClickSupport listener;
    private List<Item> items;
    private Context context;
    private RecyclerView recyclerView;
    public final int TYPE_ITEM = 0;
    public final int TYPE_LOAD = 1;
    F2_Adapter.OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

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
        //set publisher name
        if (item.getPublisher() != null) {
            new ASyncProfessionalClass(objects -> {
                String id = objects[0].toString();
                try {
                    Server.getUserProfile(id, objects1 -> {
                        objects[0] = new Gson().fromJson(objects1[0].toString(), Person.class);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, objects -> {
                String name = item.getPublisher();//((Person) objects[0]).getName();
                publisherName.setText(name);

            }).execute(item.getPublisher());
        }
        //set population
        if (item.getEvent() != null)
            population.setText("Attenders : " + item.getAttenders().size() + " / " + item.getEvent().getATTENDER_NUMBER_RANGE());
        //set image
        image.setImageResource(R.drawable.mytestimage);
        //set publisher image
        Picasso.with(holder.view.getContext()).load(R.drawable.testimagetwo).resize(90, 90)
                .transform(new CropCircleTransformation()).into(publisherPhoto);
        //set category desc
        String sentence = "";
        if (item.getEvent() instanceof CinemaEvent) {
            sentence = "Watch " + ((CinemaEvent) item.getEvent()).getFilmName();
        } else if (item.getEvent() instanceof RestaurantEvent) {
            sentence = "Serve for " + Converter.toCamelCase(((RestaurantEvent) item.getEvent()).getMealMode().toString());
        } else if (item.getEvent() instanceof TripEvent) {
            sentence = "Trip to " + item.getEvent().getLocation().getExtras().getString("NAME");
        }
        category.setText(sentence);
        //set stickerLabel
        if (item.getEvent() != null)
            if (Calendar.getInstance().after(item.getEvent().getEndDate()))
                stickerLabel.setText("Join");
            else
                stickerLabel.setText("Expired");
        //set endDate
        if (item.getEvent() != null)
            startDate.setText(Converter.getDifferenceBetweenCalendars(Calendar.getInstance(), item.getEvent().getEndDate()));
        //
        listener.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                openInSummaryMode(v, items.get(position));
            }

            @Override
            public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {
                Item item = items.get(position);
                item.getAttenders().add(Setting.decode_Default(Helper.encryptedUserID));
                Server.editItemDetails(item.getDatabaseID(), item, objects -> {
                    Item newItem = null;
                    try {

                        newItem = new Gson().fromJson(objects[1].toString(), Item.class);
                    } catch (Exception e) {
                        Log.d(Helper.LOG_TOUCH_ERROR, "Error WHILE GSON");

                    }
                    if (objects[0].toString().equals("done")) {
                        Toast.makeText(v.getContext(), "Successfully edited", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(v.getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }

                });
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
            intent.putExtra("Item", new Gson().toJson(item));
            String type = item.getEvent() instanceof CinemaEvent ? "cinema" : (item.getEvent() instanceof TripEvent ? "trip" : "restaurant");
            intent.putExtra("Type", new Gson().toJson(type));
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
