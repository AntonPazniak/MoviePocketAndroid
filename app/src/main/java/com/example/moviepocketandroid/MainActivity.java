package com.example.moviepocketandroid;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.TMDB.MovieTMDBApi;

import java.util.List;


public class MainActivity extends AppCompatActivity{

    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private TextView movieTitleTextView;
    private TextView movieOverviewTextView;
    private TextView movieReleaseDateTextView;
    private TextView movieGenresTextView;
    private LinearLayout homeButton;
    private LinearLayout searchButton;
    private LinearLayout youButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }

}
