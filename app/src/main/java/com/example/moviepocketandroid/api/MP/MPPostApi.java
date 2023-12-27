package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.util.LocalDateAdapter;
import com.example.moviepocketandroid.util.LocalDateTimeAdapter;
import com.example.moviepocketandroid.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import okhttp3.OkHttpClient;

public class MPPostApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();


}
