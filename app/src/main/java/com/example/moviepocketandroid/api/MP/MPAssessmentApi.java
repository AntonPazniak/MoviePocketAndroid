package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.util.LocalDateAdapter;
import com.example.moviepocketandroid.util.StringUnit;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDate;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The class is responsible for receiving information from the MoviePocket server about
 * favorite films about the films that the user wants to watch and which he has watched.
 */

public class MPAssessmentApi {

    private static final String baseUrl = StringUnit.baseServerUrl;

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();


    /**
     * Requests to the server get , post, getAll FavoriteMovie
     */

    public static String postFavoriteMovie(int idMovie) {

        String url = baseUrl + "/movies/favorite/set";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

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
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean getFavoriteMovie(int idMovie) {
        String url = baseUrl + "/movies/favorite/get?idMovie=" + idMovie;

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

    public static int[] getAllFavoriteMovie() {
        String url = baseUrl + "/movies/favorite/all";

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
                return gson.fromJson(responseString, int[].class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{};
    }


    public static Boolean getToWatchMovie(int idMovie) {
        String url = baseUrl + "/movies/towatch/get?idMovie=" + idMovie;

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


    public static String postToWatchMovie(int idMovie) {
        String url = baseUrl + "/movies/towatch/set";

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
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] getAllToWatchMovie() {
        String url = baseUrl + "/movies/towatch/all";

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
                return gson.fromJson(responseString, int[].class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{};
    }

    public static Boolean getWatchedMovie(int idMovie) {
        String url = baseUrl + "/movies/watched/get?idMovie=" + idMovie;

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


    public static String postWatchedMovie(int idMovie) {
        String url = baseUrl + "/movies/watched/set";

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
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] getAllWatchedMovie() {
        String url = baseUrl + "/movies/watched/allByUser";

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
                return gson.fromJson(responseString, int[].class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{};
    }


    public static Boolean postMovieTracing(int idMovie) {
        String url = baseUrl + "/movies/tracking/set";

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

    public static Boolean getMovieTracing(int idMovie) {
        String url = baseUrl + "/movies/tracking/get?idMovie=" + idMovie;

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
                return gson.fromJson(responseString, Boolean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
