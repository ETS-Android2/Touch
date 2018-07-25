package com.seeds.touch.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.Notification;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.Management.Manager.MainActivity;
import com.seeds.touch.Management.Manager.SendNotification;
import com.seeds.touch.R;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Server.ServiceGenerator3;
import com.seeds.touch.Technical.Enums;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import net.hockeyapp.android.metrics.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
            Toast.makeText(this, "" + id, Toast.LENGTH_LONG).show();
            updateFields(id);
        }

    }

    private void updateFields(String id) {
        ProfileAPI api = ServiceGenerator3.createService(ProfileAPI.class);
        Call<Person> call = api.getProfile(id);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                initializeFields(response.body());
                manageListeners(response.body());
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d(Helper.LOG_TOUCH_ERROR, "Error while getting person from server");
            }
        });
    }

    private void initializeFields(Person person) {
        if (person.getID().equalsIgnoreCase(Helper.userID))
            Helper.profileFollowButton.setVisibility(View.INVISIBLE);
        Helper.profileName.setText(person == null ? "Error" : person.getName());
        Helper.profileBiography.setText(person == null ? "Error" : person.getBiography());
        if (person == null) {
            Helper.profileFollowingsNumber.setText("-1");
            Helper.profileFollowersNumber.setText("-1");
            return;
        }
        if (person.getFollowings() != null)
            Helper.profileFollowingsNumber.setText(person.getFollowings().size() + "");
        if (person.getFollowers() != null)
            Helper.profileFollowersNumber.setText(person.getFollowers().size() + "");
        if (person.getFollowers().contains(Helper.userID)) {
            Helper.profileFollowButton.setText("Unfollow");
        } else {
            Helper.profileFollowButton.setText("Follow");
        }


    }

    private void manageListeners(Person person) {
        Helper.profileFollowButton.setOnClickListener(v -> {
            //update
            Call<Person> call = ServiceGenerator.createService(ProfileAPI.class).getProfile(Helper.userID);
            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    Person me = response.body();
                    Log.d("DFWW", GSON_Wrapper.getInstance().toJson(me));
                    HashSet<String> hashSet = me.getFollowings();
                    if (hashSet.contains(person.getID())) {
                        hashSet.remove(person.getID());
                        Log.d("DFWW1", GSON_Wrapper.getInstance().toJson(hashSet));
                        Map<String, String> map = new HashMap<>();
                        map.put("ID", Helper.userID);
                        map.put("Followings", GSON_Wrapper.getInstance().toJson(hashSet));
                        Call<Integer> call2 = ServiceGenerator.createService(ProfileAPI.class).updateProfile(map);
                        call2.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                switch (Enums.UpdateUserResult.values()[response.body()]) {
                                    case SUCCESSFUL:
                                        Toast.makeText(v.getContext(), "LeVEL1", Toast.LENGTH_LONG).show();
                                        updateSecondPersonProfile(v, person.getID(), Helper.userID,false);
                                        break;
                                    case FAILED:
                                        Toast.makeText(v.getContext(), "ERROR", Toast.LENGTH_LONG).show();
                                        break;
                                    case ID_EXIST:
                                        Toast.makeText(v.getContext(), "ERROR2", Toast.LENGTH_LONG).show();
                                        break;
                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("GVBC", "SDFX");
                            }
                        });
                    } else {

                        HashSet<String> map = me.getFollowings();
                        map.add(person.getID());
                        Log.d("DFWW3", GSON_Wrapper.getInstance().toJson(person));
                        Map<String, String> params = new HashMap<>();
                        params.put("ID", Helper.userID);
                        params.put("Followings", GSON_Wrapper.getInstance().toJson(map));
                        Call<Integer> call2 = ServiceGenerator.createService(ProfileAPI.class).updateProfile(params);
                        call2.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                switch (Enums.UpdateUserResult.values()[response.body()]) {
                                    case SUCCESSFUL:
                                        Toast.makeText(v.getContext(), "LeVEL1prime", Toast.LENGTH_LONG).show();
                                        updateSecondPersonProfile(v, person.getID(), Helper.userID,true);
                                        break;
                                    case FAILED:
                                        Toast.makeText(v.getContext(), "ERRORprime", Toast.LENGTH_LONG).show();
                                        break;
                                    case ID_EXIST:
                                        Toast.makeText(v.getContext(), "ERROR2prime", Toast.LENGTH_LONG).show();
                                        break;
                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("GVBC", "SDFX");
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {
                    Log.d("GFHF", "SASD");
                }
            });
        });
    }

    private void updateSecondPersonProfile(View v, String selfID, String doerID,boolean startFollowing_NOTUNFOLLOWING) {
        Call<Person> callT = ServiceGenerator.createService(ProfileAPI.class).getProfile(selfID);
        callT.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Person he=response.body();
                Log.d("DFWW2", GSON_Wrapper.getInstance().toJson(he));
                HashSet<String> set = he.getFollowers();
                if (set.contains(doerID)) {
                    set.remove(doerID);
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", he.getID());
                    map.put("Followers", GSON_Wrapper.getInstance().toJson(set));
                    Call<Integer> call2 = ServiceGenerator.createService(ProfileAPI.class).updateProfile(map);
                    call2.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            switch (Enums.UpdateUserResult.values()[response.body()]) {
                                case SUCCESSFUL:
                                    MainActivity.sendNotification(v.getContext(),he.getID(),"Touch",doerID+(startFollowing_NOTUNFOLLOWING? " Started " : "Canceled")+" Following you");
                                    updateFields(selfID);
                                    break;
                                case FAILED:
                                    Toast.makeText(v.getContext(), "ERRORR", Toast.LENGTH_LONG).show();
                                    break;
                                case ID_EXIST:
                                    Toast.makeText(v.getContext(), "ERRORR2", Toast.LENGTH_LONG).show();
                                    break;
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.d("GVBC", "SDFX");
                        }
                    });
                } else {
                    HashSet<String> hashSet=he.getFollowers();
                    hashSet.add(doerID);
                    Log.d("FGHC",doerID+"    "+GSON_Wrapper.getInstance().toJson(hashSet));
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", he.getID());
                    map.put("Followers", GSON_Wrapper.getInstance().toJson(hashSet));
                    Call<Integer> call2 = ServiceGenerator.createService(ProfileAPI.class).updateProfile(map);
                    call2.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            switch (Enums.UpdateUserResult.values()[response.body()]) {
                                case SUCCESSFUL:
                                    Toast.makeText(v.getContext(),"FINIIISHED",Toast.LENGTH_LONG).show();
                                    break;
                                case FAILED:
                                    Toast.makeText(v.getContext(), "ERRORRas", Toast.LENGTH_LONG).show();
                                    break;
                                case ID_EXIST:
                                    Toast.makeText(v.getContext(), "ERRORR2as", Toast.LENGTH_LONG).show();
                                    break;
                            }

                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.d("GVBC", "SDFX");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d("GFHF", "SASD");
            }
        });
    }

    private void findViews() {
        Helper.profileName = findViewById(R.id.profile_name);
        Helper.profileBiography = findViewById(R.id.profile_biography);
        Helper.profileFollowingsNumber = findViewById(R.id.profile_following_num);
        Helper.profileFollowersNumber = findViewById(R.id.profile_followers_num);
        Helper.profileFollowButton = findViewById(R.id.profile_follow_btn);
    }
}
