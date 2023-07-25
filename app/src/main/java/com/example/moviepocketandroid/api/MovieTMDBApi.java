package com.example.moviepocketandroid.api;

import static com.example.moviepocketandroid.api.models.Actor.parseActor;
import static com.example.moviepocketandroid.api.models.Actor.parsePopularActor;
import static com.example.moviepocketandroid.api.models.Movie.parseMovieInfo;
import static com.example.moviepocketandroid.api.models.Movie.parseMoviePopular;

import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.MovieImage;
import com.example.moviepocketandroid.api.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieTMDBApi {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZGEzNWQ1OGZkMTI0OTdiMTExZTRkZDFjNGE0YzAwNCIsInN1YiI6IjY0NDUyZGMwNjUxZmNmMDYxNzliZmY5YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.expCnsMxBP9wfZab438BOkfl0VPQJftRFG7WPkSRyD0";

    public Movie getMovieDetails(int movieId) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "/movie/" + movieId)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return parseMovieInfo(responseBody);
            } else {
                // Handle error response
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Movie> getPopularMovies() {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=1da35d58fd12497b111e4dd1c4a4c004&language=en-US&page=1";

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
                int sizeOfPopularMovie =  20; // Получить размер массива результатов
                for (int i = 0; i < sizeOfPopularMovie; i++) {
                    JSONObject movieObject = resultsArray.getJSONObject(i);
                    Movie movie = parseMoviePopular(movieObject.toString());
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
    public List<Actor> getActorsByIdMovie(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<Actor> actors = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/"+idMovie+"/credits?api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
    public List<Movie> getSimilarMoviesById(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/"+idMovie+"/similar?api_key=1da35d58fd12497b111e4dd1c4a4c004";

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

    public List<Actor> getPopularActors() {
        OkHttpClient client = new OkHttpClient();
        List<Actor> actors = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/person/popular?api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Actor actor = parsePopularActor(movieObject.toString());
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

    public List<Movie> getSearchResults(String query) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movies = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=1da35d58fd12497b111e4dd1c4a4c004&query="+query;

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
                    Movie movie = Movie.parsMovie(movieObject.toString());
                    if (movie != null) {
                        movies.add(movie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }


    public List<MovieImage> getImagesByIdMovie(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<MovieImage> images = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/"+idMovie+"/images?api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
