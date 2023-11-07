package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.Movie;
import com.google.common.net.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The class is responsible for receiving information from the MoviePocket server about
 * favorite films about the films that the user wants to watch and which he has watched.
 */

public class MPAssessmentAPI {

    String baseUrl = "http://moviepocket.projektstudencki.pl";
    OkHttpClient client = new OkHttpClient();

    /**
     * Requests to the server get , post, getAll FavoriteMovie
     */

    public String postFavoriteMovie(int idMovie) {

        String url = baseUrl + "/movies/favorite/set";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody requestBody = new FormBody.Builder()
                .add("idMovie", String.valueOf(idMovie))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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

    public Boolean getFavoriteMovie(int idMovie) {
        String url = baseUrl + "/movies/favorite/get?idMovie=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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

    public int[] getAllFavoriteMovie() {
        String url = baseUrl + "/movies/favorite/all";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String str = response.body().string();
                if (str.equals("[]"))
                    return new int[]{};
                else {
                    return parsStringToArr(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{};
    }


    public Boolean getToWatchMovie(int idMovie) {
        String url = baseUrl + "/movies/towatch/get?id=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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


    public String postToWatchMovie(int idMovie) {
        String url = baseUrl + "/movies/towatch/set";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody requestBody = new FormBody.Builder()
                .add("id", String.valueOf(idMovie))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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

    public int[] getAllToWatchMovie() {
        String url = baseUrl + "/movies/towatch/all";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String str = response.body().string();
                if (str.equals("[]"))
                    return new int[]{};
                else {
                    return parsStringToArr(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{};
    }

    public Boolean getWatchedMovie(int idMovie) {
        String url = baseUrl + "/movies/watched/get?idMovie=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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


    public String postWatchedMovie(int idMovie) {
        String url = baseUrl + "/movies/watched/set";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody requestBody = new FormBody.Builder()
                .add("idMovie", String.valueOf(idMovie))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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

    public int[] getAllWatchedMovie() {
        String url = baseUrl + "/movies/watched/allByUser";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String str = response.body().string();
                if (str.equals("[]"))
                    return new int[]{};
                else {
                    return parsStringToArr(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{};
    }


    public int[] parsStringToArr(String str) {
        str = str.replaceAll("\\[|\\]|\\s", "");
        String[] numberStrings = str.split(",");
        int[] numbers = new int[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            numbers[i] = Integer.parseInt(numberStrings[i].trim());
        }
        return numbers;
    }


}
