package com.seeds.touch.Configuration;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.Enums.LoginStatus;
import com.seeds.touch.Technical.Helper;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public final class Setting {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    public static final String USER_INFORMATION_SHARED_PREFERENCES_TABLE = "USER_INFORMATION";

    private Setting() {
    }

    public static void checkForPermissions(Context context,String... permissions) {
//        TedRxPermission.with(context)
//                .setDeniedMessage(
//                        "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(permissions)
//                .request().subscribe(tedPermissionResult -> {
//            if (tedPermissionResult.isGranted()) {
//                Log.d("Touch_Permission", "Permission Granted");
//            } else {
//                Log.d("Touch_Permission", "Permission Denied" + tedPermissionResult.getDeniedPermissions().toString());
//            }
//        });
        Dexter.withActivity((Activity) context)
                .withPermissions(permissions).withListener(DialogOnAnyDeniedMultiplePermissionsListener.Builder
                .withContext(context)
                .withTitle("Permissions")
                .withMessage("Permissions should be granted :)")
                .withButtonText(android.R.string.ok)
                .withIcon(R.mipmap.setting_icon)
                .build()).check();
    }

    public static LoginStatus getLoginStatus(Context context) {
        preferences = context.getSharedPreferences(USER_INFORMATION_SHARED_PREFERENCES_TABLE, MODE_PRIVATE);
        editor = preferences.edit();
        return preferences.getString(Helper.LOGIN_STATUS_KEY, LoginStatus.NEW.toString()).equals(LoginStatus.USER.toString()) ? LoginStatus.USER : LoginStatus.NEW;
    }

    public static void saveSetting(Context context, String table, String key, String value) {
        preferences = context.getSharedPreferences(table, MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        editor.apply();
    }

    public static String loadSetting(Context context, String table, String key, String defaultValue) {
        preferences = context.getSharedPreferences(table, MODE_PRIVATE);
        String value = preferences.getString(key, defaultValue);
        return value;
    }

    public static void setStatusBarInDualColored(Context context) {
        //dual color
        if (Build.VERSION.SDK_INT >= 23) {
            ((Activity) (context)).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    public static void hideStatusBar(Context context,Enums.DisplayMode displayStatus) {
        switch (displayStatus)
        {
            case SHOW:
                if (Build.VERSION.SDK_INT < 16) {
                    ((Activity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                else {
                    View decorView = ((Activity)context).getWindow().getDecorView();
                    // Show Status Bar.
                    int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                    decorView.setSystemUiVisibility(uiOptions);
                }
                break;
            case HIDE:
                if (Build.VERSION.SDK_INT < 16) {
                    ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                else {
                    View decorView = ((Activity)context).getWindow().getDecorView();
                    // Hide Status Bar.
                    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(uiOptions);
                }
                break;
        }
    }

    public static String decode_Default(String str) {
        if (str == null)
            return str;
        return new String(Base64.decode(str, Base64.DEFAULT));
    }

    public static String encode_Default(String str) {
        if (str == null)
            return str;
        return new String(Base64.encodeToString(str.getBytes(), Base64.DEFAULT));
    }
}
