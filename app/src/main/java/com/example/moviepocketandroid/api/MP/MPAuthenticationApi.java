package com.example.moviepocketandroid.api.MP;

import android.webkit.CookieManager;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MPAuthenticationApi {

    private static String baseUrl = "http://moviepocket.projektstudencki.pl";

    public static String getCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(baseUrl);
        if (cookie != null)
            return cookie;
        else return "";
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

    public boolean postLogin(String email, String password) {
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

                        CookieManager cookieManager = CookieManager.getInstance();
                        cookieManager.setCookie(baseUrl, authToken);
                    }
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean postRegistration(String username, String email, String password) {
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

    public Boolean existsUserByEmail(String email) {
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

    public Boolean existsUserByUsername(String username) {
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


    public boolean postLostPassword(String username) {
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
