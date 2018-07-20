package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Management.Interface.HomeItemAPI;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCinemaItemFragment extends Fragment {
    private final static int RANGE = 40;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_cinema_item, container, false);
        findViews(view);
        fillSpinners(view);
        handleListeners(view);
        return view;
    }

    private void fillSpinners(View view) {
        List<String> list = new ArrayList<>();
        for (Enums.AccessType item : Enums.AccessType.values())
            list.add(item.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                view.getContext(), android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Helper.cinemaAccessTypeSpinner.setAdapter(adapter);
////////////////////////
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < RANGE; i++)
            list2.add(i);

        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(
                view.getContext(), android.R.layout.simple_spinner_item, list2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Helper.cinemaRangeSpinner.setAdapter(adapter2);


    }

    private void handleListeners(View view) {
        Helper.cinemaSaveTextView.setOnClickListener(v -> {
            Item item = new Item();
            Call<String> api = ServiceGenerator.createService(HomeItemAPI.class).storeItem("SiminTala", GSON_Wrapper.getInstance().toJson(item));
            api.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body().equalsIgnoreCase("donw")) {
                        Toast.makeText(view.getContext(), "Cinema icon successfully added", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(), "Cinema icon failed to add", Toast.LENGTH_LONG).show();
                    }
                    MainActivity.openActivity_GeneralMode(v.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(Helper.LOG_TOUCH_ERROR, "Error while add iteming :)");
                }
            });
        });
        Helper.cinemaCancelTextView.setOnClickListener(v -> {
            MainActivity.openActivity_GeneralMode(v.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
        });

    }

    private void findViews(View view) {
        Helper.cinemaFilmNameEdittext = view.findViewById(R.id.add_cinema_item_title);
        Helper.cinemaDescriptionEdittext = view.findViewById(R.id.add_cinema_item_description);
        Helper.cinemaLocationEdittext = view.findViewById(R.id.add_cinema_item_location);
        Helper.cinemaFilmNameEdittext = view.findViewById(R.id.add_cinema_item_film_name);
        Helper.cinemaDateTextView = view.findViewById(R.id.add_cinema_item_choose_date);
        Helper.cinemaAccessTypeSpinner = view.findViewById(R.id.add_cinema_item_accesstype);
        Helper.cinemaSaveTextView = view.findViewById(R.id.add_cinema_item_save_icon);
        Helper.cinemaCancelTextView = view.findViewById(R.id.add_cinema_item_back_icon);
    }
}
