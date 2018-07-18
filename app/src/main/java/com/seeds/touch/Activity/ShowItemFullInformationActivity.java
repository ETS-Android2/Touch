package com.seeds.touch.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Fragment.Others.DetailFragment_Cinema;
import com.seeds.touch.Fragment.Others.DetailFragment_Restaurant;
import com.seeds.touch.Fragment.Others.DetailFragment_Trip;
import com.seeds.touch.Fragment.Others.LoginFragment;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.LocationDeserializer;
import com.seeds.touch.Technical.LocationSerializer;

public class ShowItemFullInformationActivity extends AppCompatActivity {
    private static String type;
    private static String GSONitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_full_information);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Location.class, new LocationDeserializer());
        gsonBuilder.registerTypeAdapter(Location.class, new LocationSerializer());
        Gson gson = gsonBuilder.create();

        type=gson.fromJson(getIntent().getStringExtra("Type"),String.class);
        GSONitem=getIntent().getStringExtra("Item");
        Toast.makeText(this,"IS : "+GSONitem,Toast.LENGTH_LONG).show();
        Fragment fragment = null ;

        if(type.equals("cinema"))
        {
            fragment=new DetailFragment_Cinema();
        }
        else if(type.equals("trip"))
        {
            fragment=new DetailFragment_Trip();
        }
        else if(type.equals("restaurant"))
        {
            fragment=new DetailFragment_Restaurant();
        }
        Bundle bundle=new Bundle();
        bundle.putString("Item",GSONitem);
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.item_detail_monitor, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        MainActivity.openActivity_GeneralMode(this, Enums.ActivityRepository.MAIN_ACTIVITY,true);
    }
}
