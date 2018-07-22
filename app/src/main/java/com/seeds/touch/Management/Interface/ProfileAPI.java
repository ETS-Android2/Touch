package com.seeds.touch.Management.Interface;

import com.seeds.touch.Entity.Entities.Item;
import com.seeds.touch.Entity.Entities.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileAPI {
    @GET("getProfileInformation.php")
    Call<Person> getProfile(@Query("id") String profileID);
    @GET("updateProfileInformation.php")
    Call<Integer> updateProfile(@Query("id") String profileID,@Query("numberOfEditedColumns") int n,@Query("columns") String GSONColumnsSet,@Query("values") String GSONValues);
    @GET("login_userprofile.php")
    Call<Integer> loginProfile(@Query("id") String profileID,@Query("password") String password);

}
