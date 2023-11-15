package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.MovieList;
import com.example.moviepocketandroid.util.StringUnit;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MPListApi {

    private static final String baseUrl = StringUnit.baseServerUrl;
    private static OkHttpClient client = new OkHttpClient();


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
                Gson gson = new Gson();
                return gson.fromJson(responseString, MovieList.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MovieList> getAllListExistIdMovie(int idMovie) {
        List<MovieList> lists = new ArrayList<>();
        String url = baseUrl + "/movies/list/getAllByIdMovie?idMovie=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONArray reviewArray = new JSONArray(responseBody);

                Gson gson = new Gson();

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
