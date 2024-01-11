/*
 *
 *  * ******************************************************
 *  *  Copyright (C) MoviePocket <prymakdn@gmail.com>
 *  *  This file is part of MoviePocket.
 *  *  MoviePocket can not be copied and/or distributed without the express
 *  *  permission of Danila Prymak, Alexander Trafimchyk and Anton Pozniak
 *  * *****************************************************
 *
 */

package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.review.Review;
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

public class MPReviewApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static final OkHttpClient client = new OkHttpClient();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static Review getReviewById(int idReview) {
        Review review = null;
        String url = baseUrl + "/review/get?idReview=" + idReview;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseString = response.body().string();
                return gson.fromJson(responseString, Review.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return review;
    }

    public static Boolean getAuthorship(int idReview) {
        String url = baseUrl + "/review/authorship?idReview=" + idReview;

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

    public static List<Review> getReviewAllByIdMovie(int idMovie) {
        List<Review> reviews = new ArrayList<>();
        String url = baseUrl + "/review/movie/all?idMovie=" + idMovie;

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
                    Review review = gson.fromJson(reviewObject.toString(), Review.class);
                    if (review != null) {
                        reviews.add(review);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }


    public static Boolean postReviewMovie(int idMovie, String title, String content) {
        String url = baseUrl + "/review/movie/set?idMovie=" + idMovie + "&title=" + title;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(content, JSON);

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

    public static List<Review> getReviewAllByIdList(int idList) {
        List<Review> reviews = new ArrayList<>();
        String url = baseUrl + "/review/list/all?idList=" + idList;

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
                    Review review = gson.fromJson(reviewObject.toString(), Review.class);
                    if (review != null) {
                        reviews.add(review);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public static Boolean postReviewList(int idList, String title, String content) {
        String url = baseUrl + "/review/list/set?idList=" + idList + "&title=" + title;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(content, JSON);

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


    public static List<Review> getReviewAllByIdPost(int idPost) {
        List<Review> reviews = new ArrayList<>();
        String url = baseUrl + "/review/post/all?idPost=" + idPost;

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
                    Review review = gson.fromJson(reviewObject.toString(), Review.class);
                    if (review != null) {
                        reviews.add(review);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public static Boolean postReviewPost(int idPost, String title, String content) {
        String url = baseUrl + "/review/post/set?idPost=" + idPost + "&title=" + title;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(content, JSON);

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


    public static Boolean delReviewMovie(int idReview) {
        String url = baseUrl + "/review/del";

        RequestBody requestBody = new FormBody.Builder()
                .add("idReview", String.valueOf(idReview))
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

    public static Boolean editReviewMovie(int idReview, String title, String content) {
        String url = baseUrl + "/review/up?idReview=" + idReview + "&title=" + title;

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


    public static Boolean getLike(int idReview) {
        String url = baseUrl + "/review/like?idReview=" + idReview;

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

    public static Boolean setLike(int idReview, Boolean likeOrDis) {
        String url = baseUrl + "/review/like";

        RequestBody requestBody = new FormBody.Builder()
                .add("idReview", String.valueOf(idReview))
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

    public static int[] getCountLikes(int idReview) {
        String url = baseUrl + "/review/likes?idReview=" + idReview;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseString = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(responseString, int[].class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{0, 0};
    }


}
