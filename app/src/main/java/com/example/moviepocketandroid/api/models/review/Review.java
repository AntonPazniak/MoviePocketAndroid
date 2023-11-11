package com.example.moviepocketandroid.api.models.review;


import android.annotation.SuppressLint;

import com.example.moviepocketandroid.api.models.BaseEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {

    private String title;
    private String content;
    private String username;
    private String dataCreated;
    private String dataUpdated;
    private int idMovie;
    private int id;
    private int[] likes;


    public Review(String title, String content, String username, String dataCreated, String dataUpdated, int idMovie, int id, int[] likes) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.dataCreated = dataCreated;
        this.dataUpdated = dataUpdated;
        this.idMovie = idMovie;
        this.id = id;
        this.likes = likes;
    }

    // Создайте конструктор, который принимает JSON-строку и парсит Review объект из нее
    public static Review parseReview(String reviewJson) {
        Review review = null;
        try {
            JSONObject jsonObject = new JSONObject(reviewJson);
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");
            String username = jsonObject.getString("username");
            String dataCreatedStr = jsonObject.getString("dataCreated");
            String dataUpdatedStr = jsonObject.getString("dataUpdated");
            int idMovie = jsonObject.getInt("idMovie");
            int id = jsonObject.getInt("id");

            String dataCreated = dataCreatedStr.substring(0, 10) + " " + dataCreatedStr.substring(11, 16);
            String dataUpdated = dataUpdatedStr.substring(0, 10) + " " + dataUpdatedStr.substring(11, 16);

            // Преобразование массива JSON в массив целых чисел (предполагается, что вы имеете метод для этого)
            int[] likes = parseJSONArrayToIntegerArray(jsonObject.getJSONArray("likes"));

            review = new Review(title, content, username, dataCreated, dataUpdated, idMovie, id, likes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return review;
    }


    // Метод для парсинга JSON-массива в массив целых чисел
    private static int[] parseJSONArrayToIntegerArray(JSONArray jsonArray) {
        int[] result = new int[jsonArray.length()];
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result[i] = jsonArray.getInt(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getDataCreated() {
        return dataCreated;
    }

    public String getDataUpdated() {
        return dataUpdated;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public int getId() {
        return id;
    }

    public int[] getLikes() {
        return likes;
    }
}
