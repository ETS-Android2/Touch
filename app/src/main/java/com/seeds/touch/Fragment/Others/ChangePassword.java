package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.CurrentTimeInterface;
import com.seeds.touch.Management.Interface.PostTimeInterface;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.Helper;

import java.io.IOException;

import static com.seeds.touch.Configuration.Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE;

public class ChangePassword extends Fragment {
    private Button submitButton;
    private Person person;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        findViews(view);
        person = new Gson().fromJson(getArguments().getString("PERSON"), Person.class);
        handleListeners(view);
        return view;
    }

    private void handleListeners(View view) {
        String newPasswordText = ((EditText) view.findViewById(R.id.new_password_edittext)).getText().toString();
        String newPasswordConfirmedText = ((EditText) view.findViewById(R.id.new_password_confirm_edittext)).getText().toString();
        if (newPasswordText.equals(newPasswordConfirmedText)) {
            person.setPassword(newPasswordText);
            Server.editUserAccount(person.getID(), person, Volley.newRequestQueue(view.getContext()), (CurrentTimeInterface) objects -> {
                Server.loginUserProfile(view.getContext(), Volley.newRequestQueue(view.getContext()), person, objects1 -> {
                    if (objects1[0].toString().equals("done")) {
                        Setting.saveSetting(view.getContext(), USER_INFORMATION_SHARED_PREFERENCES_TABLE, Helper.ENCRYPTED_USER_ID_KEY, Helper.encryptedUserID);
                        Setting.saveSetting(view.getContext(), USER_INFORMATION_SHARED_PREFERENCES_TABLE, Helper.LOGIN_STATUS_KEY, Enums.LoginStatus.USER.toString());
                        Toast.makeText(view.getContext(), "Welcome " + person.getID(), Toast.LENGTH_SHORT).show();
                        MainActivity.openActivity_GeneralMode(view.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
                    }
                });
            });
        } else {
            Toast.makeText(view.getContext(), "Not Match Pass", Toast.LENGTH_LONG).show();
        }
    }

    private void findViews(View view) {
        submitButton = (Button) view.findViewById(R.id.submitButton);

    }
}
