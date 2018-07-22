package com.seeds.touch.Management.Interface;

import com.seeds.touch.Entity.Entities.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeItemAPI {
    @GET("getHomeItems.php")
    Call<List<Item>> getItems(@Query("index") int index,@Query("followings") String GSONFollowings);
    @GET("storeItem.php")
    Call<Integer> storeItem(@Query("publisher") String publisher,
                           @Query("eventKey") String eventKey,
                           @Query("event") String event,
                           @Query("attenders") String attenders,
                           @Query("comments") String comments,
                           @Query("status") String status,
                           @Query("tags") String tags,
                           @Query("accessType") String accessType,
                           @Query("pictures") String pictures,
                           @Query("rank") float rank,
                           @Query("loadItem") String loadItem
                           );
}
