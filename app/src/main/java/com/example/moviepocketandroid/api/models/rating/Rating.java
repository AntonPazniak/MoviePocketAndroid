package com.example.moviepocketandroid.api.models.rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {

    private double rating;
    private int count;

    public Rating() {
    }
    public Rating(double rating, int count) {
        this.rating = rating;
        this.count = count;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
