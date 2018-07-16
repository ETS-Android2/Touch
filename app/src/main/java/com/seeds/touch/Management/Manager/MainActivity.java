package com.seeds.touch.Management.Manager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.seeds.touch.Activity.CompleteUserProfile;
import com.seeds.touch.Activity.SettingActivity;
import com.seeds.touch.Activity.UserVerify;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Fragment.Fragment1.Fragment1;
import com.seeds.touch.Fragment.Fragment2.Fragment2;
import com.seeds.touch.Fragment.Fragment3.Fragment3;
import com.seeds.touch.Fragment.Fragment4.Fragment4;
import com.seeds.touch.R;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.Helper;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.seeds.touch.Technical.Enums.LoginStatus.NEW;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* Setting.checkForPermissions(this, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION);*/
        if (!Setting.getLoginStatus(this).equals(NEW)) {
            openActivity_GeneralMode(this, Enums.ActivityRepository.LOG_IN, true);
        } else {
            Helper.encryptedUserID = Setting.loadSetting(this, Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE,
                    Helper.ENCRYPTED_USER_ID_KEY, Helper.NO_VALUE_FOUND_FOR_KEY + "\"" + Helper.ENCRYPTED_USER_ID_KEY + "\"");
            constructMainActivityItems(savedInstanceState);
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                updateScreenOnlineDetails();
            }, 0, 100, TimeUnit.MILLISECONDS);
        }
    }

    private void updateScreenOnlineDetails() {
        //code
    }

    private void constructMainActivityItems(Bundle savedInstanceState) {
        Setting.setStatusBarInDualColored(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Helper.reNewObjects(this);
        findViews();
        manageListeners();
        refineSizes();
        Thread thread = new Thread(() -> getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.main_place_holder, new Fragment1())
                .commit());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();
    }

    private void refineSizes() {
    }

    private void manageListeners() {
        createNavigationBar();
        Helper.setting_toolbar_icon.setOnClickListener(view -> openActivity_GeneralMode(
                Helper.MainActivity_context, Enums.ActivityRepository.SETTING, false));
        Helper.filter_toolbar_icon.setOnClickListener(view -> createFilterDialog());
    }

    private void createNavigationBar() {
        Helper.bottomNavigation.addItem(Helper.item1);
        Helper.bottomNavigation.addItem(Helper.item2);
        Helper.bottomNavigation.addItem(Helper.item3);
        Helper.bottomNavigation.addItem(Helper.item4);
        Helper.bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        Helper.bottomNavigation.setBehaviorTranslationEnabled(false);
        Helper.bottomNavigation.setAccentColor(getResources().getColor(R.color.enabled_navigation_color));
        Helper.bottomNavigation.setInactiveColor(getResources().getColor(R.color.disabled_navigation_color));
        Helper.bottomNavigation.setForceTint(true);// Force to tint the drawable (useful for font with icon for example)
        Helper.bottomNavigation.setTranslucentNavigationEnabled(true);
        Helper.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        Helper.bottomNavigation.setColored(false);
        Helper.bottomNavigation.setCurrentItem(0);
        Helper.bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
        AHNotification notification = new AHNotification.Builder()
                .setText("0")
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(this, R.color.white))
                .build();
        Helper.bottomNavigation.setNotification(notification, 3);
        Helper.bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {

            if (position >= 1)
                Helper.filter_toolbar_icon.setVisibility(View.INVISIBLE);
            else// if (Helper.filter_toolbar_icon.getVisibility() == View.INVISIBLE)
                Helper.filter_toolbar_icon.setVisibility(View.VISIBLE);

            String tabName = getTabNameAtPosition(position);
            Helper.tab_details_text_view.setText(tabName);

            Fragment selectedFragment = null;
            switch (Enums.Tabs.values()[position]) {
                case FIRST:
                    selectedFragment = new Fragment1();
                    break;
                case SECOND:
                    selectedFragment = new Fragment2();
                    break;
                case THIRD:
                    selectedFragment = new Fragment3();
                    Bundle bundle = new Bundle();
                    bundle.putString("Person_ID", Helper.encryptedUserID);
                    selectedFragment.setArguments(bundle);
                    break;
                case FORTH:
                    selectedFragment = new Fragment4();
                    break;
            }

            final Fragment finalSelectedFragment = selectedFragment;
            Thread thread = new Thread(() -> getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.main_place_holder, finalSelectedFragment)
                    .commit());
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.start();
            return true;
        });

    }

    private String getTabNameAtPosition(int position) {
        switch (position) {
            case 0:
                return getStringValueOf(R.string.home);
            case 1:
                return getStringValueOf(R.string.world);
            case 2:
                return getStringValueOf(R.string.profile);
            case 3:
                return getStringValueOf(R.string.news);
        }
        return "Error";

    }

    private void createFilterDialog() {
        AlertDialog alertDialog1;
        final CharSequence[] options = new String[]{"Publisher", "Date", "Shuffle"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.sort_by);
        builder.setSingleChoiceItems(options, -1, (dialog, item) -> {
            int tabNum = Helper.bottomNavigation.getCurrentItem();
            if (tabNum == 0) {
//                Item_Manager.getInstance().readItems(MainActivity.this, Enums.EventTypes.HOME_PUBLISHED, objects -> {
//                    filterItems(objects[0], item, tabNum);
//                });
            }
            else {
//                Item_Manager.getInstance().readItems(MainActivity.this, Enums.EventTypes.WORLD_PUBLISHED, objects -> {
//                    filterItems(objects[0], item, tabNum);
//                });
            }

            dialog.dismiss();


        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void findViews() {
        Helper.bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        Helper.tab_details_text_view = (TextView) findViewById(R.id.word_tab_name_in_toolbar);
        Helper.total_items_Details_in_toolbar_text_view = (TextView) findViewById(R.id.total_items_Details_in_toolbar);
        Helper.setting_toolbar_icon = (ImageView) findViewById(R.id.setting_symbol_imageview_toolbar);
        Helper.filter_toolbar_icon = (ImageView) findViewById(R.id.filter_symbol_imageview_toolbar);
    }

    public static void openActivity_GeneralMode(Context context, Enums.ActivityRepository activity, boolean killCurrentActivity) {
        Intent toOpenIntent = null;
        switch (activity) {
            case MAIN_ACTIVITY:
                toOpenIntent = new Intent(context, MainActivity.class);
                break;
            case LOG_IN:
                toOpenIntent = new Intent(context, UserVerify.class);
                break;
            case COMPLETE_USER_PROFILE:
                toOpenIntent = new Intent(context, CompleteUserProfile.class);
                break;

//            case ADD_CINEMA_ITEM:
////                toOpenIntent = new Intent(context, AddItemActivity_Cinema.class);
////                break;
////            case ADD_RESTAURANT_ITEM:
////                toOpenIntent = new Intent(context, AddItemActivity_Restaurant.class);
////                break;
////            case ADD_TRAVEL_ITEM:
////                toOpenIntent = new Intent(context, AddItemActivity_Travel.class);
////                break;
            case SETTING:
                toOpenIntent = new Intent(context, SettingActivity.class);
                break;
            default:
                toOpenIntent = null;
                break;
        }
        if (toOpenIntent != null) {
            toOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(toOpenIntent);
            if (killCurrentActivity)
                ((Activity) context).finish();
        }
    }

    private void checkForCrashes() {

        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {

        UpdateManager.unregister();
    }

    @NonNull
    public static String getStringValueOf(int id) {
        return Helper.MainActivity_resource.getString(id);
    }

}
