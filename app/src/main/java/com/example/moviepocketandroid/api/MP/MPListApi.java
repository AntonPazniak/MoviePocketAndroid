package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.MovieList;
import com.example.moviepocketandroid.util.LocalDateAdapter;
import com.example.moviepocketandroid.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MPListApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
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
        String url = baseUrl + "/movies/list/getAllListsContainingMovie?idMovie=" + idMovie;

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


}
