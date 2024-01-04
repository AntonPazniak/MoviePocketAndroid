package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.util.LocalDateAdapter;
import com.example.moviepocketandroid.util.LocalDateTimeAdapter;
import com.example.moviepocketandroid.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MPListApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static MovieList getListById(int idList) {
        String url = baseUrl + "/movies/list/get?idMovieList=" + idList;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, MovieList.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MovieList> getAllListExistIdMovie(int idMovie) {
        List<MovieList> lists = new ArrayList<>();
        String url = baseUrl + "/movies/list/movie/containing?idMovie=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONArray reviewArray = new JSONArray(responseBody);
                for (int i = 0; i < reviewArray.length(); i++) {
                    JSONObject reviewObject = reviewArray.getJSONObject(i);
                    MovieList movieList = gson.fromJson(reviewObject.toString(), MovieList.class);
                    if (movieList != null) {
                        lists.add(movieList);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }

    public static Boolean getAuthorship(int idList) {
        String url = baseUrl + "/movies/list/authorship?idList=" + idList;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationApi.getCookies())
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return !response.body().string().equals("false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean editList(int idList, String title, String content) {
        String url = baseUrl + "/movies/list/up?idMovieList=" + idList + "&title=" + title;

        // Create JSON payload
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(content, JSON);

        // Build the request
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationApi.getCookies())
                .addHeader("Content-Type", "application/json")
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

    public static MovieList newList(String title, String content) {
        String url = baseUrl + "/movies/list/set?title=" + title;


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(content, JSON);


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationApi.getCookies())
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, MovieList.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean getLike(int idList) {
        String url = baseUrl + "/movies/list/like/get?idList=" + idList;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationApi.getCookies())
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return !response.body().string().equals("false");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean setLike(int idList, Boolean likeOrDis) {
        String url = baseUrl + "/movies/list/like/set";

        RequestBody requestBody = new FormBody.Builder()
                .add("idList", String.valueOf(idList))
                .add("like", String.valueOf(likeOrDis))
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
