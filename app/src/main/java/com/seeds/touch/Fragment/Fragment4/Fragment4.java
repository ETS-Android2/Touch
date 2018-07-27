package com.seeds.touch.Fragment.Fragment4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.seeds.touch.Adapter.F4_Adapter;
import com.seeds.touch.Entity.Entities.News;
import com.seeds.touch.Entity.Entities.Person;
import com.seeds.touch.Management.Interface.ProfileAPI;
import com.seeds.touch.R;
import com.seeds.touch.Server.ServiceGenerator;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment4 extends Fragment {

    private LinkedList<News> items = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment4, container, false);
        findViews(view);
        populateRecyclerView(view);
    
        return view;
    }

    private void populateRecyclerView(View view) {
        Helper.newsLayoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        Helper.newsRecyclerView.setLayoutManager(Helper.newsLayoutManager);
        Helper.newsRecyclerView.setHasFixedSize(true);

        Call<Person> call= ServiceGenerator.createService(ProfileAPI.class).getProfile(Helper.userID);
        List<News> list=new LinkedList<>();
       // list.add(new News("Salam1", Calendar.getInstance()));
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                list.addAll(response.body().getNews());
                Helper.newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d("DFGX","DFSFGV");
            }
        });

        Helper.newsAdapter=new F4_Adapter(list);
        Helper.newsRecyclerView.setAdapter(Helper.newsAdapter);


    }

    private void findViews(View view) {
        Helper.newsRecyclerView=view.findViewById(R.id.news_recyclerView);
    }


}
