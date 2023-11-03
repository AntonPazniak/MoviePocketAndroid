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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MPApi {

    public List<Review> getReviewAllByIdMovie(int idMovie) {
        OkHttpClient client = new OkHttpClient();
        List<Review> reviews = new ArrayList<>();
        String url = "http://moviepocket.projektstudencki.pl/movies/review/getAllByMovie?idMovie=" + idMovie;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            System.out.println(response);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println(responseBody);
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


}
