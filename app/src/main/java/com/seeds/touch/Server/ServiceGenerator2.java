package com.seeds.touch.Server;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator2 {
    public static <S> S createService(Class<S> serviceClass) {

        OkHttpClient httpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://panel.pushe.co/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit.create(serviceClass);

    }
}
