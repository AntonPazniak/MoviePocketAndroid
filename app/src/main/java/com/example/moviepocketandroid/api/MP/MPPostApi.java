package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.post.Post;
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

public class MPPostApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static Post getPostById(int idPost) {
        String url = baseUrl + "/post/get?idPost=" + idPost;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, Post.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Post> getAllPostExistIdMovie(int idPost) {
        List<Post> posts = new ArrayList<>();
        String url = baseUrl + "/post/movie?idMovie=" + idPost;

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
                    Post post = gson.fromJson(reviewObject.toString(), Post.class);
                    if (post != null) {
                        posts.add(post);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public static Boolean getAuthorship(int idPost) {
        String url = baseUrl + "/post/authorship?idPost=" + idPost;

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

    public static Boolean getLike(int idPost) {
        String url = baseUrl + "/post/like?idPost=" + idPost;

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

    public static Boolean setLike(int idPost, Boolean likeOrDis) {
        String url = baseUrl + "/post/setLike";

        RequestBody requestBody = new FormBody.Builder()
                .add("idPost", String.valueOf(idPost))
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


    public static Boolean editPost(int idPost, String title, String content) {
        String url = baseUrl + "/post/up?idPost=" + idPost + "&title=" + title;

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

    public static Post newPostMovie(int idMovie, String title, String content) {
        String url = baseUrl + "/post/movie/set?idMovie=" + idMovie + "&title=" + title;

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
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, Post.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Post newPostPerson(int idPerson, String title, String content) {
        String url = baseUrl + "/post/person/set?idPerson=" + idPerson + "&title=" + title;

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
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, Post.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Post> getAllPostExistIdPerson(int idPerson) {
        List<Post> posts = new ArrayList<>();
        String url = baseUrl + "/post/person?idPerson=" + idPerson;

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
                    Post post = gson.fromJson(reviewObject.toString(), Post.class);
                    if (post != null) {
                        posts.add(post);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return posts;
    }


}
