package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.VisibleForTesting;
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
import com.seeds.touch.Entity.Events.CinemaEvent;
import com.seeds.touch.Entity.Events.Event;
import com.seeds.touch.Management.Interface.HomeItemAPI;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCinemaItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private final static int RANGE = 20;
    private Calendar calendar = Calendar.getInstance();
    private CinemaEvent event=new CinemaEvent();

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
        for (int i = 1; i <= RANGE; i++)
            list2.add(i);

        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(
                view.getContext(), android.R.layout.simple_spinner_item, list2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Helper.cinemaRangeSpinner.setAdapter(adapter2);


    }

    private void handleListeners(View view) {
        Helper.cinemaDateTextView.setOnClickListener(v ->
        {
            TimePickerDialog.newInstance(AddCinemaItemFragment.this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
            DatePickerDialog.newInstance(AddCinemaItemFragment.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
        });
        Helper.cinemaSaveTextView.setOnClickListener(v -> {
            Item item = new Item();


            event.setFilmName(Helper.cinemaFilmNameEdittext.getText().toString());
            event.setDescription(Helper.cinemaDescriptionEdittext.getText().toString());
            event.setLocation( Helper.cinemaLocationEdittext.getText().toString());
            event.setStartDate(calendar);
            event.setEndDate(null);
            event.setTitle(Helper.cinemaTitleEdittext.getText().toString());
            event.setATTENDER_NUMBER_RANGE(Integer.parseInt(Helper.cinemaRangeSpinner.getSelectedItem().toString()));
            //event.setStartDate();

            item.setLoadItem(false);
            item.setPublisher(Helper.userID);
            item.setEventKey("cinema");
            item.setJSONAccessType(Helper.cinemaAccessTypeSpinner.getSelectedItem().toString());
            Log.d("FGHV",GSON_Wrapper.getInstance().toJson(event));
            item.setJSONEvent(GSON_Wrapper.getInstance().toJson(event));
            item.setJSONStatus(GSON_Wrapper.getInstance().toJson(Enums.Status.SHOWN));


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
                    Log.d("VCV",response.body().toString());
                    if (response.body().toString().equalsIgnoreCase("1")) {
                        Toast.makeText(view.getContext(), "Cinema event successfully added", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(), "Cinema event failed to add", Toast.LENGTH_LONG).show();
                    }
                    MainActivity.openActivity_GeneralMode(v.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.d(Helper.LOG_TOUCH_ERROR, "Error while add iteming :)");
                }
            });
        });
        Helper.cinemaCancelTextView.setOnClickListener(v -> {
            MainActivity.openActivity_GeneralMode(v.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
        });

    }

    private void findViews(View view) {
        Helper.cinemaTitleEdittext = view.findViewById(R.id.add_cinema_item_title);
        Helper.cinemaDescriptionEdittext = view.findViewById(R.id.add_cinema_item_description);
        Helper.cinemaLocationEdittext = view.findViewById(R.id.add_cinema_item_location);
        Helper.cinemaFilmNameEdittext = view.findViewById(R.id.add_cinema_item_film_name);
        Helper.cinemaDateTextView = view.findViewById(R.id.add_cinema_item_choose_date);
        Helper.cinemaAccessTypeSpinner = view.findViewById(R.id.add_cinema_item_accesstype);
        Helper.cinemaRangeSpinner = view.findViewById(R.id.add_cinema_item_attenders);
        Helper.cinemaSaveTextView = view.findViewById(R.id.add_cinema_item_save_icon);
        Helper.cinemaCancelTextView = view.findViewById(R.id.add_cinema_item_back_icon);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        Helper.cinemaDateTextView.setText(calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+
        calendar.get(Calendar.DAY_OF_MONTH)+"   "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
        event.setStartDate(calendar);
        event.setEndDate(null);
    }
}
