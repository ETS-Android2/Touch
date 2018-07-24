package com.seeds.touch.Management.Interface;

import com.seeds.touch.Entity.Entities.Person;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProfileAPI {
    @GET("getProfileInformation.php")
    Call<Person> getProfile(@Query("id") String profileID);
    @GET("login_userprofile.php")
    Call<Integer> loginProfile(@Query("id") String profileID,@Query("password") String password);
    @POST("updateProfileInformation.php")
    @FormUrlEncoded
    Call<Integer> updateProfile(@FieldMap Map<String,String> params);
    @POST("register_userprofile.php")
    @FormUrlEncoded
    Call<Integer> registerProfile(@FieldMap Map<String,String> params);


}