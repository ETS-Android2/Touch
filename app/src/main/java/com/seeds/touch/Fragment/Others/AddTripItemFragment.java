package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Entity.Events.TripEvent;
import com.seeds.touch.Management.Interface.HomeItemAPI;
import com.seeds.touch.Management.Interface.PostTimeInterface;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTripItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private final static int RANGE = 30;
    private Calendar calendar = Calendar.getInstance();
    private TripEvent event = new TripEvent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_trip_item, container, false);
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
        Helper.tripAccessTypeSpinner.setAdapter(adapter);
////////////////////////
        List<Integer> list2 = new ArrayList<>();
        for (int i = 1; i <= RANGE; i++)
            list2.add(i);

        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(
                view.getContext(), android.R.layout.simple_spinner_item, list2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Helper.tripRangeSpinner.setAdapter(adapter2);


////////////////////////


    }

    private void handleListeners(View view) {
        Helper.tripDateTextView.setOnClickListener(v ->
        {
            TimePickerDialog.newInstance(AddTripItemFragment.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
            DatePickerDialog.newInstance(AddTripItemFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
        });
        Helper.tripSaveTextView.setOnClickListener(v -> {
            Item item = new Item();

            event.setDescription(Helper.tripDescriptionEdittext.getText().toString());
            event.setLocation(Helper.tripLocationEdittext.getText().toString());
            event.setStartDate(calendar);
            event.setEndDate(null);
            event.setTitle(Helper.tripTitleEdittext.getText().toString());
            event.setATTENDER_NUMBER_RANGE(Integer.parseInt(Helper.tripRangeSpinner.getSelectedItem().toString()));
            //event.setStartDate();

            item.setLoadItem(false);
            item.setPublisher(Helper.userID);
            item.setEventKey("trip");
            item.setJSONAccessType(GSON_Wrapper.getInstance().toJson(Helper.tripAccessTypeSpinner.getSelectedItem()));
            item.setJSONEvent(GSON_Wrapper.getInstance().toJson(event));
            Log.d("DFGCV",GSON_Wrapper.getInstance().toJson(event));
            item.setJSONStatus(GSON_Wrapper.getInstance().toJson(Enums.Status.SHOWN));

                Log.d("GFHH2", GSON_Wrapper.getInstance().toJson(item.getComments()));

            Call<Integer> call = ServiceGenerator.createService(HomeItemAPI.class).storeItem(
                    item.getPublisher(),
                    item.getEventKey(),
                    GSON_Wrapper.getInstance().toJson(item.getEvent(item.getEventKey())),
                    GSON_Wrapper.getInstance().toJson(item.getAttenders()),
                    GSON_Wrapper.getInstance().toJson(item.getComments()),
                    GSON_Wrapper.getInstance().toJson(item.getStatus()),
                    GSON_Wrapper.getInstance().toJson(item.getTags()),
                    GSON_Wrapper.getInstance().toJson(item.getAccessType()),
                    GSON_Wrapper.getInstance().toJson(item.getPictures()),
                    item.getRank(),
                    GSON_Wrapper.getInstance().toJson(item.isLoadItem()));
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("GFHH", response.body() + "");
                        if (response.body().toString().equalsIgnoreCase("1")) {
                            Toast.makeText(view.getContext(), "Trip event successfully added", Toast.LENGTH_LONG).show();
                            MainActivity.openActivity_GeneralMode(v.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
                        } else {
                            Toast.makeText(view.getContext(), "Trip event failed to add", Toast.LENGTH_LONG).show();
                        }

                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d("SDFG", "ERRORROROR");
                }
            });

        });
        Helper.tripCancelTextView.setOnClickListener(v -> {
            MainActivity.openActivity_GeneralMode(v.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
        });

    }

    private void findViews(View view) {
        Helper.tripTitleEdittext = view.findViewById(R.id.add_trip_item_title);
        Helper.tripDescriptionEdittext = view.findViewById(R.id.add_trip_item_description);
        Helper.tripLocationEdittext = view.findViewById(R.id.add_trip_item_location);
        Helper.tripDateTextView = view.findViewById(R.id.add_trip_item_choose_date);
        Helper.tripAccessTypeSpinner = view.findViewById(R.id.add_trip_item_accesstype);
        Helper.tripRangeSpinner = view.findViewById(R.id.add_trip_item_attenders);
        Helper.tripSaveTextView = view.findViewById(R.id.add_trip_item_save_icon);
        Helper.tripCancelTextView = view.findViewById(R.id.add_trip_item_back_icon);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Helper.tripDateTextView.setText(calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.DAY_OF_MONTH) + "   " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        event.setStartDate(calendar);
        event.setEndDate(null);
    }
}