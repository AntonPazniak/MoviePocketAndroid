package com.example.moviepocketandroid.api.MP;

import android.webkit.CookieManager;

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
        String url = "http://moviepocket.projektstudencki.pl/login";

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

}
