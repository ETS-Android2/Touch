package com.seeds.touch.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Fragment.Others.AddCinemaItemFragment;
import com.seeds.touch.Fragment.Others.AddRestaurantItemFragment;
import com.seeds.touch.Fragment.Others.AddTripItemFragment;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Technical.Enums;

public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Setting.setStatusBarInDualColored(this);


        setContentView(R.layout.activity_add_item);
        String type=getIntent().getExtras().getString("Type");
        Fragment fragment=type.equals("Restaurant") ? new AddRestaurantItemFragment() :
                (type.equals("Cinema")?  new AddCinemaItemFragment() : new AddTripItemFragment());

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.add_item_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        MainActivity.openActivity_GeneralMode(this, Enums.ActivityRepository.MAIN_ACTIVITY,true);
    }
}
