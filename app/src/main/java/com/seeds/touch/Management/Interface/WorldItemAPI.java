package com.seeds.touch.Management.Interface;

import com.seeds.touch.Entity.Entities.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WorldItemAPI {
    @GET("getItems.php")
    Call<List<Item>> getItems(@Query("index") int index);
}
