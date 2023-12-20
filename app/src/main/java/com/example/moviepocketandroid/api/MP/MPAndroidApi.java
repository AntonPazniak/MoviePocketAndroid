package com.example.moviepocketandroid.api.MP;

import android.net.Uri;

import com.example.moviepocketandroid.util.Utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MPAndroidApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static final OkHttpClient client = new OkHttpClient();


    public static String getData(String urlTMDB) {

        String encodedUrl = Uri.encode(urlTMDB);

        String url = baseUrl + "/android/?url=" + encodedUrl;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
