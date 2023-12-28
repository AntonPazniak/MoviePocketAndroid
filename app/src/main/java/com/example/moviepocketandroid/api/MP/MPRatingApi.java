package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.util.Utils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MPRatingApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static OkHttpClient client = new OkHttpClient();

    public static int getRatingUserByIdMovie(int idMovie) {
        String url = baseUrl + "/movies/rating/get?idMovie=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationApi.getCookies())
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(responseString, int.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Double getRatingMovie(int idMovie) {
        String url = baseUrl + "/movies/rating/getByIdMovie?idMovie=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(responseString, Double.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Boolean postRatingUserByIdMovie(int idMovie, int rating) {
        String url = baseUrl + "/movies/rating/set";

        RequestBody requestBody = new FormBody.Builder()
                .add("idMovie", String.valueOf(idMovie))
                .add("rating", String.valueOf(rating))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationApi.getCookies())
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean delRatingUserByIdMovie(int idMovie) {
        String url = baseUrl + "/movies/rating/del";

        RequestBody requestBody = new FormBody.Builder()
                .add("idMovie", String.valueOf(idMovie))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationApi.getCookies())
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
