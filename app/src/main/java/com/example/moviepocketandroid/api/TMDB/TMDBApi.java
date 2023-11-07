package com.example.moviepocketandroid.api.TMDB;

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

public class TMDBApi {

    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZGEzNWQ1OGZkMTI0OTdiMTExZTRkZDFjNGE0YzAwNCIsInN1YiI6IjY0NDUyZGMwNjUxZmNmMDYxNzliZmY5YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.expCnsMxBP9wfZab438BOkfl0VPQJftRFG7WPkSRyD0";

    private static final String language = "us-US";

    public Movie getInfoMovie(int id) {
        if (id > 0)
            return getMovieDetails(id);
        else
            return getTVsInfo(Math.abs(id));
    }

    public List<Actor> getActorsByIdMovie(int id) {
        if (id > 0)
            return getActorsByIdFilm(id);
        else
            return getActorsByIdTV(Math.abs(id));
    }
    public List<MovieImage> getImagesByIdMovie(int id){
        if (id > 0)
            return getImagesByIdFilm(id);
        else
            return getImagesByIdTV(Math.abs(id));
    }
    public List<Movie> getSimilarMoviesById(int id){
        if (id > 0)
            return getSimilarFilmsById(id);
        else
            return getSimilarTVById(Math.abs(id));
    }
    private Movie getMovieDetails(int movieId) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
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
    public List<Actor> getActorsByIdFilm(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<Actor> actors = new ArrayList<>();
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

    private List<Movie> getSimilarFilmsById(int idMovie) {
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

    public List<Movie> getSearchResultsMovie(String query) {
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


    private List<MovieImage> getImagesByIdFilm(int idMovie) {
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


    public String getMovieTrailerUrl(int movieId) {
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

    public List<Movie> getNowPlayingMovie() {
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

    public List<Movie> getPopularTVs() {
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
                    Movie tv = Movie.parseTVSeriesPopularInfo(movieObject.toString());
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


    public Movie getTVsInfo(int idTV) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/tv/" + idTV + "?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return Movie.parseTVSeriesInfo(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Actor> getActorsByIdTV(int idTV) {
        OkHttpClient client = new OkHttpClient();
        List<Actor> actors = new ArrayList<>();
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

    private List<Movie> getSimilarTVById(int idTV) {
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
                    Movie tv = Movie.parsTVSeries(movieObject.toString());
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

    public List<Movie> getSearchResultsTV(String query) {
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
                    JSONObject tvObject = resultsArray.getJSONObject(i);
                    Movie tvSeries = Movie.parsTVSeries(tvObject.toString());
                    if (tvSeries != null) {
                        tvSeriesList.add(tvSeries);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return tvSeriesList;
    }

    public Actor getPersonById(int idPerson) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/person/" + idPerson + "?language=" + language + "&api_key=1da35d58fd12497b111e4dd1c4a4c004";
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

    public List<Movie> getTVByIdActor(int idActor) {
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
                    Movie tv = Movie.parsTVSeries(movieObject.toString());
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

    public List<Actor> getSearchResultsPerson(String query) {
        OkHttpClient client = new OkHttpClient();
        List<Actor> actors = new ArrayList<>();
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
                    Actor actor = Actor.parsePopularActor(actorObject.toString());
                    if (actor != null)
                        actors.add(actor);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return actors;
    }

}
