package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.review.Review;
import com.example.moviepocketandroid.util.StringUnit;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MPReviewApi {

    private static final String baseUrl = StringUnit.baseServerUrl;
    private static OkHttpClient client = new OkHttpClient();

    public static Review getReviewById(int idReview) {
        Review review = null;
        String url = baseUrl + "/movies/review/get?idReview=" + idReview;

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
                return gson.fromJson(responseString, Review.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return review;
    }

    public static Boolean getAuthorship(int idReview) {
        String url = baseUrl + "/movies/review/getAuthorship?idReview=" + idReview;

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
        String url = baseUrl + "/movies/review/getAllByMovie?idMovie=" + idMovie;

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
        List<Review> reviews = new ArrayList<>();
        String url = baseUrl + "/movies/review/set";

        RequestBody requestBody = new FormBody.Builder()
                .add("idMovie", String.valueOf(idMovie))
                .add("title", title)
                .add("content", content)
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

    public static Boolean delReviewMovie(int idReview) {
        String url = baseUrl + "/movies/review/del";

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
        String url = baseUrl + "/movies/review/up";

        RequestBody requestBody = new FormBody.Builder()
                .add("idReview", String.valueOf(idReview))
                .add("title", title)
                .add("content", content)
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

    public static Boolean getLike(int idReview) {
        String url = baseUrl + "/movies/review/getLike?idReview=" + idReview;

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
        String url = baseUrl + "/movies/review/setLike";

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
        String url = baseUrl + "/movies/review/getAllLike?idReview=" + idReview;

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
