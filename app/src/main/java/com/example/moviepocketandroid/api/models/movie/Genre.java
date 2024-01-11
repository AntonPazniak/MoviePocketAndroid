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

package com.example.moviepocketandroid.api.models.movie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genre implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    private static String getGenreText(int genreId) {
        String genreText;
        switch (genreId) {
            case 28:
                genreText = "Action";
                break;
            case 12:
                genreText = "Adventure";
                break;
            case 16:
                genreText = "Animation";
                break;
            case 35:
                genreText = "Comedy";
                break;
            case 80:
                genreText = "Crime";
                break;
            case 99:
                genreText = "Documentary";
                break;
            case 18:
                genreText = "Drama";
                break;
            case 10751:
                genreText = "Family";
                break;
            case 14:
                genreText = "Fantasy";
                break;
            case 36:
                genreText = "History";
                break;
            case 27:
                genreText = "Horror";
                break;
            case 10402:
                genreText = "Music";
                break;
            case 9648:
                genreText = "Mystery";
                break;
            case 10749:
                genreText = "Romance";
                break;
            case 878:
                genreText = "Science Fiction";
                break;
            case 10770:
                genreText = "TV Movie";
                break;
            case 53:
                genreText = "Thriller";
                break;
            case 10752:
                genreText = "War";
                break;
            case 37:
                genreText = "Western";
                break;
            default:
                genreText = "Unknown Genre";
                break;
        }
        return genreText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
