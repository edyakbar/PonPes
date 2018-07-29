package com.example.ta.ponpes.Config;

import com.example.ta.ponpes.Config.Model.UserAPIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edy akbar on 26/06/2018.
 */

public class Config {
    public static final String BASE_URL = "http://192.168.1.19/pondok/";
    public static final String URL = BASE_URL + "Api/";
    public static final String URL_IMG = BASE_URL + "image/logo/";
    public static final String URL_IMG_EVENT = BASE_URL + "image/event/";

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    private static Retrofit retrofit = null;


}
