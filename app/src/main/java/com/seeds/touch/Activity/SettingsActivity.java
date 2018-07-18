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
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Technical.Helper;

import java.util.List;
import java.util.Locale;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String PERSIAN_LOCALE="fa";
    private static final String ENGLISH_LOCALE="en_US";
    private static final String LOCALE_KEY="localekey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        final Preference persianSwitch = findPreference("Persian");
        final Locale[] locale = new Locale[1];
        persianSwitch.setOnPreferenceChangeListener((preference, o) -> {
            Resources resources=getResources();
            SharedPreferences sharedPreferences=getSharedPreferences("localePref",MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            boolean isOn=(boolean) o;
            if(isOn==true) {
                locale[0] = new Locale(PERSIAN_LOCALE);
                editor.putString(LOCALE_KEY, PERSIAN_LOCALE);
                Setting.saveSetting(getApplicationContext(), Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE,"lang",PERSIAN_LOCALE);
            }
            else {
                locale[0] = new Locale(ENGLISH_LOCALE);
                editor.putString(LOCALE_KEY, ENGLISH_LOCALE);
                Setting.saveSetting(getApplicationContext(), Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE,"lang",ENGLISH_LOCALE);
            }
            Configuration configuration=resources.getConfiguration();
            configuration.setLocale(locale[0]);
            getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
            recreate();

            return true;
        });
        String lang=Setting.loadSetting(getApplicationContext(), Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE,"lang","");
        ((SwitchPreference)findPreference("Persian")).setChecked(lang.equals(PERSIAN_LOCALE) ? true : false);

        final Preference logout=findPreference("logout");
        logout.setOnPreferenceClickListener(preference -> {
            Log.d("sarah","working");
            Setting.logout(getBaseContext());
            return true;
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        Log.d("GHC",key);
        if(!key.equalsIgnoreCase("persian") && !key.equalsIgnoreCase("english"))
        {
            String value = sharedPreferences.getString(key, null);
           /* Server.updatePerson(getApplicationContext(), Setting.decode_Default(Helper.encryptedUserID), key, value, objects -> {
                boolean successfulResult = ((String) objects[0]).equals("1") ? true : false;
                if (successfulResult)
                    Toast.makeText(getApplicationContext(), key + " Updated Successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            });*/
        }

    }
}