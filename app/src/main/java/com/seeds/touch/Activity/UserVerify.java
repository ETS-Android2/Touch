package com.seeds.touch.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Fragment.Others.LoginFragment;
import com.seeds.touch.R;

import static com.seeds.touch.Technical.Enums.DisplayMode.HIDE;

public class UserVerify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_user_verify);
        Setting.setStatusBarInDualColored(this);
        Setting.hideStatusBar(this, HIDE);
        new Thread(() -> {
            Fragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_veify_fragment, loginFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }).start();
    }
}
