package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.seeds.touch.Activity.CompleteUserProfileActivity;
import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.R;
import com.seeds.touch.Server.Server;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.Helper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.seeds.touch.Configuration.Setting.USER_INFORMATION_SHARED_PREFERENCES_TABLE;
import static com.seeds.touch.Configuration.Setting.saveUserID;

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
                Map<String,String> map=new HashMap<>();
                map.put("ID",userName);
                map.put("Password",password);
                map.put("PhoneNumber",phoneNumber);
                map.put("PushID",MainActivity.getPushNotificationID(view.getContext()));
                map.put("Followings","[\""+userName+"\"]");
                Call<Integer> call= ServiceGenerator.createService(ProfileAPI.class).registerProfile(map);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("KJMJ",response.body().toString());
                        switch (Enums.RegisterResult.values()[response.body()])
                        {
                            case SUCCESSFUL:
                                saveUserID(v.getContext(),userName);
                                Toast.makeText(v.getContext(),"Welcome "+userName,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(v.getContext(), CompleteUserProfileActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("ID", userName);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case EXIST_ID:
                                Toast.makeText(v.getContext(),"Try another ID",Toast.LENGTH_LONG).show();
                                break;
                            case ERROR:
                                Toast.makeText(v.getContext(),"Unknown Error ",Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("LJJ","errrror");
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
