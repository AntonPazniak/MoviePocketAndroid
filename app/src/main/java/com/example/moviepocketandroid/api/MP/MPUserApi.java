package com.example.moviepocketandroid.api.MP;

import com.example.moviepocketandroid.api.models.user.User;
import com.example.moviepocketandroid.api.models.user.UserPage;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MPUserApi {

    private static String baseUrl = "http://moviepocket.projektstudencki.pl";
    private static OkHttpClient client = new OkHttpClient();

    public static User getUserInfo() {
        String url = baseUrl + "/user/edit/getUserDto";

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
                return gson.fromJson(responseString, User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean setUsername(String username) {
        String url = baseUrl + "/user/edit/newUsername";

        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
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

    public static Boolean setEmail(String email) {
        String url = baseUrl + "/user/edit/newEmail";

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
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

    public static Boolean setBio(String bio) {
        String url = baseUrl + "/user/edit/newBio";

        RequestBody requestBody = new FormBody.Builder()
                .add("bio", bio)
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

    public static UserPage getUserByUsername(String username) {
        String url = baseUrl + "/user/" + username;

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
                return gson.fromJson(responseString, UserPage.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
