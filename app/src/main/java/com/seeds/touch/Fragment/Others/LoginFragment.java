package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        handleButtonListeners(view);
        return view;
    }

    private void handleButtonListeners(View view) {
        Helper.login_signInButton.setOnClickListener(v -> {
            String userIdentityField = Helper.login_UsernameEditText.getText().toString();
            String password = Helper.login_PasswordEditText.getText().toString();
            if (userIdentityField != null && !userIdentityField.isEmpty() && password != null && !password.isEmpty()) {
                Person person = new Person();
                person.setID(userIdentityField);
                person.setPassword(password);
                Server.loginUserProfile(view.getContext(), Volley.newRequestQueue(view.getContext()), person, objects1 -> {
                    if (objects1[0].toString().equals("done")) {
                        Person personProfile = (Person) objects1[1];
                        Helper.encryptedUserID = Setting.encode_Default(personProfile.getID());
                        Setting.saveSetting(view.getContext(), USER_INFORMATION_SHARED_PREFERENCES_TABLE, Helper.ENCRYPTED_USER_ID_KEY, Helper.encryptedUserID);
                        Setting.saveSetting(view.getContext(), USER_INFORMATION_SHARED_PREFERENCES_TABLE, Helper.LOGIN_STATUS_KEY, Enums.LoginStatus.USER.toString());
                        Toast.makeText(view.getContext(), "Welcome " + personProfile.getID(), Toast.LENGTH_SHORT).show();
                        MainActivity.openActivity_GeneralMode(view.getContext(), Enums.ActivityRepository.MAIN_ACTIVITY, true);
                    } else {
                        Toast.makeText(view.getContext(), "Error while log in", Toast.LENGTH_LONG).show();
                    }
                });
            } else
                Toast.makeText(view.getContext(), "Empty Fields Should Be Filled", Toast.LENGTH_LONG).show();
        });
        Helper.login_registerButton.setOnClickListener(v -> {
            Fragment registerFragment = new RegisterFragment();
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.user_veify_fragment, registerFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

    }

    private void findViews(View view) {
        Helper.login_signInButton = (Button) view.findViewById(R.id.login_login_button);
        Helper.login_registerButton = (Button) view.findViewById(R.id.login_sign_up_button);
        Helper.login_UsernameEditText = (EditText) view.findViewById(R.id.login_username_edittext);
        Helper.login_PasswordEditText = (EditText) view.findViewById(R.id.login_password_edittext);
        Helper.login_Facebook_Register = (Button) view.findViewById(R.id.login_login_with_facebook);
        Helper.login_Twitter_Facebook = (Button) view.findViewById(R.id.login_login_with_twitter);
        Helper.login_Google_Facebook = (Button) view.findViewById(R.id.login_login_with_google_plus);
    }
}
