package com.example.moviepocketandroid.api.MP;

import static com.example.moviepocketandroid.api.models.Actor.parseActor;
import static com.example.moviepocketandroid.api.models.review.Review.parseReview;

import android.util.Log;

import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.review.Review;

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

    String baseUrl = "http://moviepocket.projektstudencki.pl";
    OkHttpClient client = new OkHttpClient();

    public Review getReviewById(int idReview) {
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
                String responseBody = response.body().string();
                review = parseReview(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return review;
    }

    public Boolean getAuthorship(int idReview) {
        String url = baseUrl + "/movies/review/getAuthorship?idReview=" + idReview;

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

    public List<Review> getReviewAllByIdMovie(int idMovie) {
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
                for (int i = 0; i < reviewArray.length(); i++) {
                    JSONObject reviewObject = reviewArray.getJSONObject(i);
                    Review review = parseReview(reviewObject.toString());
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

    public Boolean postReviewMovie(int idMovie, String title, String content) {
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
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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

    public Boolean delReviewMovie(int idReview) {
        String url = baseUrl + "/movies/review/del";

        RequestBody requestBody = new FormBody.Builder()
                .add("idReview", String.valueOf(idReview))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Cookie", MPAuthenticationAPI.getCookies())
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

}