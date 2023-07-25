package com.example.moviepocketandroid.api;

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

public class ActorTMDBApi {

    public Actor getPersonById(int idPerson) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/person/"+idPerson+"?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return Actor.parsPerson(responseBody);
            } else {
                // Handle error response
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Movie> getMoviesByIdActor(int idActor) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/person/"+idActor+"/movie_credits?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray resultsArray = jsonObject.getJSONArray("cast");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);
                    Movie movie = Movie.parsMovie(movieObject.toString());
                    if (movie != null) {
                        movieList.add(movie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public List<TVSeries> getTVByIdActor(int idActor) {
        OkHttpClient client = new OkHttpClient();
        List<TVSeries> tvList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/person/"+idActor+"/tv_credits?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray resultsArray = jsonObject.getJSONArray("cast");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);
                    TVSeries tv = TVSeries.pars(movieObject.toString());
                    if (tv != null) {
                        tvList.add(tv);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return tvList;
    }

    public List<MovieImage> getImagesByIdActor(int idActor) {
        OkHttpClient client = new OkHttpClient();
        List<MovieImage> images = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/person/" + idActor + "/images?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray resultsArray = jsonObject.getJSONArray("profiles");
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
