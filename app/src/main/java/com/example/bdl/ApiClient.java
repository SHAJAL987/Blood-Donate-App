package com.example.bdl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit getRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apex.oracle.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit;
    }
    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);
        return userService;
    }
    public static UserService getUserServiceReg(){
        UserService UserService = getRetrofit().create(UserService.class);
        return UserService;
    }
    public static UserService getUserLoginService(){
        UserService UserService = getRetrofit().create(UserService.class);
        return UserService;
    }
    public static UserService updateDonateDate(){
        UserService UserService = getRetrofit().create(UserService.class);
        return UserService;
    }
    public static UserService updateBasic(){
        UserService UserService = getRetrofit().create(UserService.class);
        return UserService;
    }
    public static UserService changePassword(){
        UserService UserService = getRetrofit().create(UserService.class);
        return UserService;
    }
}
