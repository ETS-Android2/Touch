package com.seeds.touch.Fragment.Fragment3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seeds.touch.Configuration.Setting;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.R;
import com.seeds.touch.Server.ServiceGenerator3;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.seeds.touch.Technical.Helper.TAG;

public class Fragment3 extends Fragment {
    private ProfileAPI api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        findViews(view);
        tuneRecyclerView();
        api = ServiceGenerator3.createService(ProfileAPI.class);
        Toast.makeText(view.getContext(), "" + Helper.userID, Toast.LENGTH_LONG).show();
        Call<Person> call = api.getProfile(Helper.userID);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                setScreenInformation(response.body());
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e(TAG, " Rxcvesponse fot person Error " + t.getMessage());

            }
        });
        return view;
    }

    private void tuneRecyclerView() {
    }

    private void findViews(View view) {
        Helper.mainProfileName = view.findViewById(R.id.main_profile_name);
        Helper.mainProfileFollowersNumber = view.findViewById(R.id.main_profile_followers_num);
        Helper.mainProfileFollowingsNumber = view.findViewById(R.id.main_profile_following_num);
        Helper.mainProfileBiography = view.findViewById(R.id.main_profile_biography);
    }

    public void setScreenInformation(Person person) {
        Helper.mainProfileName.setText(person.getName());
        Helper.mainProfileBiography.setText(person.getBiography());
        Log.d("SDCX", GSON_Wrapper.getInstance().toJson(person.getFollowings())+"");
        if (person.getFollowingsQueue() != null)
            Helper.mainProfileFollowingsNumber.setText(""+person.getFollowings().size());
        if (person.getFollowersQueue() != null) {
            Helper.mainProfileFollowersNumber.setText(""+person.getFollowers().size());
        }

    }
}
