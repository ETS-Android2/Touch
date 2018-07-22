package com.seeds.touch.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.CurrentTimeInterface;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Server.ServiceGenerator2;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteUserProfileActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, LocationListener {
    private Person person = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_profile);
        findViews();
        initializeSpinner();
        handleListeners();
    }

    private void initializeSpinner() {
        Helper.complete_genderSpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Enums.Gender.values());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Helper.complete_genderSpinner.setAdapter(aa);
    }

    private void handleListeners() {
        Helper.complete_completeButton.setOnClickListener(v -> {
            if (Helper.complete_fullName_edittext.getText() == null ||
                    Helper.complete_fullName_edittext.getText().toString().isEmpty() ||
                    Helper.complete_ID_edittext.getText() == null ||
                    Helper.complete_ID_edittext.getText().toString().isEmpty()
                    ) {
                Toast.makeText(this, "Empty Fields Should Be Filled", Toast.LENGTH_SHORT).show();
            } else {
                person.setName(Helper.complete_fullName_edittext.getText().toString().split(" ")[0]);
                person.setLastName(Helper.complete_fullName_edittext.getText().toString().split(" ")[1]);
                person.setID(Helper.complete_ID_edittext.getText().toString());
                person.setGender(Helper.complete_genderSpinner.getSelectedItem().toString().
                        equals(Enums.Gender.MALE.toString()) ? Enums.Gender.MALE :
                        (Helper.complete_genderSpinner.getSelectedItem().toString().
                                equals(Enums.Gender.FEMALE.toString()) ? Enums.Gender.FEMALE : Enums.Gender.NONE));
                person.setMacAddress(getMacAddress());
                //   if (person.getLocation() != null) {
                HashSet<String> columns = new HashSet<>();
                columns.add("Name");
                columns.add("LastName");
                columns.add("ID");
                HashSet<String> values = new HashSet<>();
                values.add(person.getName());
                values.add(person.getLastName());
                values.add(person.getID());
                Call<Integer> call = ServiceGenerator2.createService(ProfileAPI.class).
                        updateProfile(getIntent().getExtras().getString("ID"), 3, GSON_Wrapper.getInstance().toJson(columns),
                                GSON_Wrapper.getInstance().toJson(values));
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        switch (Enums.UpdateUserResult.values()[response.body()]) {
                            case SUCCESSFUL:
                                Toast.makeText(v.getContext(), "Welcome " + person.getName(), Toast.LENGTH_LONG).show();
                                MainActivity.openActivity_GeneralMode(v.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
                                break;
                            case FAILED:
                                Toast.makeText(v.getContext(), "Error while edit profile", Toast.LENGTH_LONG).show();
                                break;
                            case ID_EXIST:
                                Toast.makeText(v.getContext(), "ID exists !", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("GVBBV", "ERROR COMPLETE");
                    }
                });
                //   }
            }
        });
    }

    private void findViews() {
        Helper.complete_completeButton = findViewById(R.id.complete_complete_button);
        Helper.complete_fullName_edittext = findViewById(R.id.complete_fullname_edittext);
        Helper.complete_ID_edittext = findViewById(R.id.complete_id_edittext);
        Helper.complete_genderSpinner = findViewById(R.id.complete_gender_spinner);

    }

    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onLocationChanged(Location location) {
        person.setLocation(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }
}
