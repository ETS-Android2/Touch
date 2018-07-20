package com.seeds.touch.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Server.ServiceGenerator3;
import com.seeds.touch.Technical.Helper;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String PERSIAN_LOCALE = "fa";
    private static final String ENGLISH_LOCALE = "en_US";
    private static final String LOCALE_KEY = "localekey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        final Preference persianSwitch = findPreference("Persian");
        final Locale[] locale = new Locale[1];
        persianSwitch.setOnPreferenceChangeListener((preference, o) -> {
            Resources resources = getResources();
            SharedPreferences sharedPreferences = getSharedPreferences("localePref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            boolean isOn = (boolean) o;
            if (isOn == false) {
                locale[0] = new Locale(PERSIAN_LOCALE);
                editor.putString(LOCALE_KEY, PERSIAN_LOCALE);
                Setting.saveSetting(getApplicationContext(), Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE, "lang", PERSIAN_LOCALE);
            } else {
                locale[0] = new Locale(ENGLISH_LOCALE);
                editor.putString(LOCALE_KEY, ENGLISH_LOCALE);
                Setting.saveSetting(getApplicationContext(), Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE, "lang", ENGLISH_LOCALE);
            }
            MainActivity.saveLocale(this,locale[0].getLanguage());
            MainActivity.changeLang(this,locale[0].getLanguage());
            recreate();
            Toast.makeText(SettingsActivity.this, R.string.please_exit_and_re_open, Toast.LENGTH_LONG).show();
            return true;
        });
        String lang = Setting.loadSetting(getApplicationContext(), Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE, "lang", "");
        ((SwitchPreference) findPreference("Persian")).setChecked(lang.equals(PERSIAN_LOCALE) ? false: true);

        handleListeners();

    }

    private void handleListeners() {
        ProfileAPI api = ServiceGenerator3.createService(ProfileAPI.class);


        findPreference("logout").setOnPreferenceClickListener(preference -> {
            Setting.logout(getBaseContext());
            return true;
        });


        findPreference("first_name").setOnPreferenceChangeListener((preference1, newValue1) -> {
            Call<String> call = api.updateProfile("AlexMohammad", "Name", newValue1.toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(SettingsActivity.this, "" + response.body(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(Helper.LOG_TOUCH_ERROR, "ERROR IN RESPONSE");
                }
            });

            return false;
        });
        findPreference("last_name").setOnPreferenceChangeListener((preference1, newValue1) -> {
            Call<String> call = api.updateProfile("AlexMohammad", "LastName", newValue1.toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(SettingsActivity.this, "" + response.body(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(Helper.LOG_TOUCH_ERROR, "ERROR IN RESPONSE");
                }
            });

            return false;
        });

        findPreference("id").setOnPreferenceChangeListener((preference1, newValue1) -> {
            Call<String> call = api.updateProfile("AlexMohammad", "ID", newValue1.toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(SettingsActivity.this, "" + response.body(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(Helper.LOG_TOUCH_ERROR, "ERROR IN RESPONSE");
                }
            });

            return false;
        });

        findPreference("biography").setOnPreferenceChangeListener((preference1, newValue1) -> {
            Call<String> call = api.updateProfile("AlexMohammad", "Bio", newValue1.toString());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(SettingsActivity.this, "" + response.body(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d(Helper.LOG_TOUCH_ERROR, "ERROR IN RESPONSE");
                }
            });

            return false;
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}