package com.example.moviepocketandroid.api.TMDB;

import static com.example.moviepocketandroid.api.models.Actor.parseActor;

import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.models.MovieImage;
import com.example.moviepocketandroid.api.models.tv.TVSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TVSeriesTMDBApi {

    public List<TVSeries> getPopularTVs(){
        OkHttpClient client = new OkHttpClient();
        List<TVSeries> tvs = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray resultsArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);
                    TVSeries tv = TVSeries.parseTVSeriesPopularInfo(movieObject.toString());
                    if (tv != null) {
                        tvs.add(tv);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return tvs;
    }


    public TVSeries getTVsInfo(int idTV) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/tv/" + idTV + "?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return TVSeries.parseTVSeriesInfo(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Actor> getActorsByIdTV(int idTV) {
        OkHttpClient client = new OkHttpClient();
        List<Actor> actors = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/"+idTV+"/credits?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray castArray = jsonObject.getJSONArray("cast");
                for (int i = 0; i < castArray.length(); i++) {
                    JSONObject actorObject = castArray.getJSONObject(i);
                    Actor actor = parseActor(actorObject.toString());
                    if (actor != null) {
                        actors.add(actor);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public List<TVSeries> getSimilarTVById(int idTV) {
        OkHttpClient client = new OkHttpClient();
        List<TVSeries> tvs = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/"+idTV+"/similar?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray resultsArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);
                    TVSeries tv = TVSeries.pars(movieObject.toString());
                    if (tv != null) {
                        tvs.add(tv);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return tvs;
    }


    public List<MovieImage> getImagesByIdTV(int idTV) {
        OkHttpClient client = new OkHttpClient();
        List<MovieImage> images = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/"+idTV+"/images?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray resultsArray = jsonObject.getJSONArray("backdrops");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);
                    MovieImage image = MovieImage.parseMovieImage(movieObject.toString());
                    if (image != null) {
                        images.add(image);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return images;
    }




}
