package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.Helper;

import static com.seeds.touch.Configuration.Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE;

public class RegisterFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        findViews(view);
        handleListeners(view);
        return view;
    }

    private void handleListeners(View view) {
        Helper.register_signUpButton.setOnClickListener(v -> {
            String phoneNumber = Helper.register_phonenumberEdittext.getText().toString();
            String userName = Helper.register_userNameEdittext.getText().toString();
            String password = Helper.register_passwordEdittext.getText().toString();
            if (phoneNumber != null && !phoneNumber.isEmpty() &&
                    userName != null && !userName.isEmpty() &&
                    password != null && !password.isEmpty()) {
                Server.registerUserProfile(view.getContext(), Volley.newRequestQueue(view.getContext()), phoneNumber, userName, password, objects -> {
                    if (objects[0].toString().equals("done")) {
                        Person person = (Person) objects[1];
                        Setting.saveUSerID(view.getContext(), person.getID());
                        Toast.makeText(view.getContext(), "Signed Up Successfully", Toast.LENGTH_LONG).show();

                        Server.loginUserProfile(view.getContext(), Volley.newRequestQueue(view.getContext()), person, objects1 -> {
                            if (objects[0].toString().equals("done")) {
                                if (person.isARawUser()) {
                                    MainActivity.openActivity_GeneralMode(view.getContext(), Enums.ActivityRepository.COMPLETE_USER_PROFILE, false);
                                } else {
                                    Setting.saveSetting(view.getContext(), USER_INFORMATION_SHARED_PREFERENCES_TABLE, Helper.LOGIN_STATUS_KEY, Enums.LoginStatus.USER.toString());
                                    Toast.makeText(view.getContext(), "Welcome " + person.getID(), Toast.LENGTH_SHORT).show();
                                    MainActivity.openActivity_GeneralMode(view.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
                                }
                            } else {
                                Toast.makeText(view.getContext(), "Error while log in", Toast.LENGTH_LONG).show();
                            }
                        });

                    } else {
                        Toast.makeText(view.getContext(), "Error While Register", Toast.LENGTH_LONG).show();
                    }
                });
            } else
                Toast.makeText(view.getContext(), "Empty Fields Should Be Filled", Toast.LENGTH_LONG).show();

        });
    }

    private void findViews(View view) {
        Helper.register_phonenumberEdittext = (EditText) view.findViewById(R.id.register_phonenumber_edittext);
        Helper.register_userNameEdittext = (EditText) view.findViewById(R.id.register_UserName_edittext);
        Helper.register_passwordEdittext = (EditText) view.findViewById(R.id.register_password_edittext);
        Helper.register_signUpButton = (Button) view.findViewById(R.id.register_sign_up_button);

    }

}
