package com.seeds.touch.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.R;
import com.seeds.touch.Server.ServiceGenerator3;
import com.seeds.touch.Technical.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowOthersProfileInformationActivity extends AppCompatActivity {

    private static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_others_profile_information);
        id = getIntent().getExtras().getString("ID", null);
        findViews();

        if (id != null) {
            Toast.makeText(this,""+id,Toast.LENGTH_LONG).show();
            ProfileAPI api = ServiceGenerator3.createService(ProfileAPI.class);
            Call<Person> call = api.getProfile(id);
            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    initializeFields(response.body());
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.d(Helper.LOG_TOUCH_ERROR, "Error while getting person from server");
                }
            });
        }

        manageListeners();
    }

    private void initializeFields(Person person) {
        Helper.profileName.setText(person==null ? "Error" : person.getName());
        Helper.profileBiography.setText(person==null ? "Error" : person.getBiography());
        if(person==null)
        {
            Helper.profileFollowingsNumber.setText("-1");
            Helper.profileFollowersNumber.setText("-1");
            return;
        }
        if (person.getFollowings() != null)
            Helper.profileFollowingsNumber.setText(person.getFollowings().size() + "");
        if (person.getFollowers() != null)
            Helper.profileFollowersNumber.setText(person.getFollowers().size() + "");


    }

    private void manageListeners() {
    }

    private void findViews() {
        Helper.profileName = findViewById(R.id.profile_name);
        Helper.profileBiography = findViewById(R.id.profile_biography);
        Helper.profileFollowingsNumber = findViewById(R.id.profile_following_num);
        Helper.profileFollowersNumber = findViewById(R.id.profile_followers_num);
    }
}
