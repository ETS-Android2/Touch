package com.seeds.touch.Management.Interface;

import com.seeds.touch.Entity.Entities.Person;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Notification {
    @GET("notifications/")
    Call<Integer> sendNotification(String json);
}
