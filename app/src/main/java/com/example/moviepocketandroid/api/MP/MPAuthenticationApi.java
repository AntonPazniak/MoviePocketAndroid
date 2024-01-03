package com.example.moviepocketandroid.api.MP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.moviepocketandroid.util.Utils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MPAuthenticationApi {

    private static final String baseUrl = Utils.baseServerUrl;
    private static final String PREF_NAME = "YourAppPreferences";
    private static final String COOKIE_KEY = "Cookies";
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void setContext(Context context) {
        if (context.getApplicationContext() != null) {
            MPAuthenticationApi.context = context.getApplicationContext();
        } else {
            MPAuthenticationApi.context = context;
        }
    }

    public static String getCookies() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(COOKIE_KEY, "");
    }

    private static void saveCookies(Context context, String cookies) {
        SharedPreferences preferences = MPAuthenticationApi.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(COOKIE_KEY, cookies);
        editor.apply();
    }

    public static Boolean checkAuth() {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/user/getAut";
        String cookie = getCookies();
        if (cookie != null) {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Cookie", cookie)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean postLogin(String email, String password) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/login";

        RequestBody requestBody = new FormBody.Builder()
                .add("username", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String authToken = response.header("Set-Cookie");
                if (authToken != null) {
                    if (authToken.contains(";")) {
                        authToken = authToken.split(";")[0];

                        // Save the cookie in SharedPreferences
                        saveCookies(context, authToken);
                    }
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean postRegistration(String username, String email, String password) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/registration";

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("username", username)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
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

    public static Boolean existsUserByEmail(String email) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/registration/exist/email?email=" + email;

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
                return gson.fromJson(responseString, boolean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Boolean existsUserByUsername(String username) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/registration/exist/username?username=" + username;

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
                return gson.fromJson(responseString, boolean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean postLostPassword(String username) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/lostpassword/setEmail";

        RequestBody requestBody = new FormBody.Builder()
                .add("email", username)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
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

    public static Boolean logout() {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "/logout";

        RequestBody requestBody = new FormBody.Builder()
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

}
