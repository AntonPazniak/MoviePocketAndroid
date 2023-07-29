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


        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieOverviewTextView = findViewById(R.id.movieOverviewTextView);
        movieReleaseDateTextView = findViewById(R.id.movieReleaseDateTextView);
        movieGenresTextView = findViewById(R.id.movieGenresTextView);
        final ImageView movieImageView = findViewById(R.id.movieImageView);
        final ImageView moviePosterImageView = findViewById(R.id.moviePosterImageView);


        // Запускаем сетевой запрос в отдельном потоке
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieTMDBApi movieApiClient = new MovieTMDBApi();
                Movie movieInfo = movieApiClient.getMovieDetails(666);

                if (movieInfo != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RequestOptions requestOptions = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(MainActivity.this)
                                    .load(POSTER_BASE_URL + movieInfo.getPosterPath())
                                    .apply(requestOptions)
                                    .into(movieImageView);
                            movieTitleTextView.setText(movieInfo.getTitle());
                            movieOverviewTextView.setText(movieInfo.getOverview());
                            movieReleaseDateTextView.setText("Release Date: " + movieInfo.getReleaseDate());

                            List<String> genres = movieInfo.getGenres();
                            StringBuilder genresBuilder = new StringBuilder();
                            for (String genre : genres) {
                                genresBuilder.append(genre).append(", ");
                            }
                            if (genresBuilder.length() > 0) {
                                genresBuilder.deleteCharAt(genresBuilder.length() - 2);  // Remove trailing comma and space
                            }
                            movieGenresTextView.setText("Genres: " + genresBuilder.toString());
                        }
                    });
                } else {
                    System.out.println("nie");
                }
            }


        }).start();


    }

}
