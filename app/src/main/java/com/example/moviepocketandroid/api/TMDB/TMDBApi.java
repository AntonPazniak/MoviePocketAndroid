package com.example.moviepocketandroid.api.TMDB;

import com.example.moviepocketandroid.api.models.ImageMovie;
import com.example.moviepocketandroid.api.models.Person;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.util.LocalDateAdapter;
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

public class TMDBApi {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String language = "us-USd";

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    public static Movie getInfoMovie(int id) {
        if (id > 0)
            return getMovieDetails(id);
        else
            return getTVsInfo(Math.abs(id));
    }

    public static List<Person> getActorsByIdMovie(int id) {
        if (id > 0)
            return getActorsByIdFilm(id);
        else
            return getActorsByIdTV(Math.abs(id));
    }

    public static List<ImageMovie> getImagesByIdMovie(int id) {
        if (id > 0)
            return getImagesByIdFilm(id);
        else
            return getImagesByIdTV(Math.abs(id));
    }

    public static List<Movie> getSimilarMoviesById(int id) {
        if (id > 0)
            return getSimilarFilmsById(id);
        else
            return getSimilarTVById(Math.abs(id));
    }

    private static Movie getMovieDetails(int movieId) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, Movie.class);
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Movie> getPopularMovies() {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/popular?" + "?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
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

    public static List<Person> getActorsByIdFilm(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<Person> actors = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/" + idMovie + "/credits?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Person person = gson.fromJson(actorObject.toString(), Person.class);
                    if (person != null) {
                        actors.add(person);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return actors;
    }

    private static List<Movie> getSimilarFilmsById(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/" + idMovie + "/similar?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
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

    public static List<Person> getPopularActors() {
        OkHttpClient client = new OkHttpClient();
        List<Person> actors = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/person/popular?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    JSONObject actorObject = resultsArray.getJSONObject(i);
                    Person person = gson.fromJson(actorObject.toString(), Person.class);
                    if (person != null) {
                        actors.add(person);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return actors;
    }

    public static List<Movie> getSearchResultsMovie(String query) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movies = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?language=" + language + "&query=" + query + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
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


    private static List<ImageMovie> getImagesByIdFilm(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<ImageMovie> images = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/" + idMovie + "/images?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject responseObject = new JSONObject(responseBody);

                if (responseObject.has("backdrops")) {
                    JSONArray imageArray = responseObject.getJSONArray("backdrops");
                    for (int i = 0; i < imageArray.length(); i++) {
                        JSONObject imageObject = imageArray.getJSONObject(i);
                        ImageMovie imageMovie = gson.fromJson(imageObject.toString(), ImageMovie.class);
                        if (imageMovie != null) {
                            images.add(imageMovie);
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return images;
    }


    public static String getMovieTrailerUrl(int movieId) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    JSONObject videoObject = resultsArray.getJSONObject(i);
                    String type = videoObject.getString("type");
                    if ("Trailer".equals(type)) {
                        String key = videoObject.getString("key");
                        return "https://www.youtube.com/embed/" + key;
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Movie> getNowPlayingMovie() {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
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

    public static List<Movie> getPopularTVs() {
        OkHttpClient client = new OkHttpClient();
        List<Movie> tvs = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/popular?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                    if (movie != null) {
                        movie.setId(movie.getId() * (-1));
                        tvs.add(movie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return tvs;
    }


    public static Movie getTVsInfo(int idTV) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/tv/" + idTV + "?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                Movie movie = gson.fromJson(responseString, Movie.class);
                movie.setId(movie.getId() * (-1));
                return movie;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Person> getActorsByIdTV(int idTV) {
        OkHttpClient client = new OkHttpClient();
        List<Person> actors = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/" + idTV + "/credits?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Person person = gson.fromJson(actorObject.toString(), Person.class);
                    if (person != null) {
                        actors.add(person);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return actors;
    }

    private static List<Movie> getSimilarTVById(int idTV) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> tvs = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/" + idTV + "/similar?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                    if (movie != null) {
                        movie.setId(movie.getId() * (-1));
                        tvs.add(movie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return tvs;
    }


    public static List<ImageMovie> getImagesByIdTV(int idTV) {
        OkHttpClient client = new OkHttpClient();
        List<ImageMovie> images = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/" + idTV + "/images?api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray imageArray = jsonObject.getJSONArray("backdrops");
                for (int i = 0; i < imageArray.length(); i++) {
                    JSONObject reviewObject = imageArray.getJSONObject(i);
                    ImageMovie imageMovie = gson.fromJson(reviewObject.toString(), ImageMovie.class);
                    if (imageMovie != null) {
                        images.add(imageMovie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return images;
    }

    public static List<Movie> getSearchResultsTV(String query) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> tvSeriesList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?query=" + query + "&language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";
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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                    if (movie != null) {
                        movie.setId(movie.getId() * (-1));
                        tvSeriesList.add(movie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return tvSeriesList;
    }

    public static Person getPersonById(int idPerson) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/person/" + idPerson + "?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, Person.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Movie> getMoviesByIdActor(int idActor) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/person/" + idActor + "/movie_credits?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
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

    public static List<Movie> getTVByIdActor(int idActor) {
        OkHttpClient client = new OkHttpClient();
        List<Movie> tvList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/person/" + idActor + "/tv_credits?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

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
                    Movie movie = gson.fromJson(movieObject.toString(), Movie.class);
                    if (movie != null) {
                        movie.setId(movie.getId() * (-1));
                        tvList.add(movie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return tvList;
    }

    public static List<ImageMovie> getImagesByIdActor(int idActor) {
        OkHttpClient client = new OkHttpClient();
        List<ImageMovie> images = new ArrayList<>();
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
                    JSONObject reviewObject = resultsArray.getJSONObject(i);
                    ImageMovie imageMovie = gson.fromJson(reviewObject.toString(), ImageMovie.class);
                    if (imageMovie != null) {
                        images.add(imageMovie);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return images;
    }

    public static List<Person> getSearchResultsPerson(String query) {
        OkHttpClient client = new OkHttpClient();
        List<Person> actors = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/person?query=" + query + "&language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";
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
                    JSONObject actorObject = resultsArray.getJSONObject(i);
                    Person person = gson.fromJson(actorObject.toString(), Person.class);
                    if (person != null) {
                        actors.add(person);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return actors;
    }

}
