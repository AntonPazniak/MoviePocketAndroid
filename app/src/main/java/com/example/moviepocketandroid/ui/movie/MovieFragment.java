package com.example.moviepocketandroid.ui.movie;

import static com.example.moviepocketandroid.animation.Animation.createAnimation;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorsAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.ImagesAdapter;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.TMDB.MovieTMDBApi;
import com.example.moviepocketandroid.api.models.MovieImage;
import com.example.moviepocketandroid.util.ButtonUntil;

import java.text.DecimalFormat;
import java.util.List;

public class MovieFragment extends Fragment {

    private MovieViewModel mViewModel;
    private ImageView imageBackPopularMovie, imagePosterPopularMovie;
    private TextView textTitlePopularMovie;
    private ImageView imageEye, imageLike, imageBackPack;
    private TextView textRating, textVoteCount, textOverview, textImages;
    private TextView textActorsRecyclerView, textMoviesRecyclerView;
    private TextView textCountry, textCategories, textMinutes;
    private ActorsAdapter actorsAdapter;
    private MovieAdapter movieAdapter;
    private ImagesAdapter movieImagesAdapter;
    private View viewYouTube, viewImages, viewActors, viewSimilar;
    private boolean isExpanded = false;
    private RecyclerView actorsRecyclerView, moviesRecyclerView, imagesRecyclerView;

    private WebView webView;


    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageBackPopularMovie = view.findViewById(R.id.imageBackMovie);
        imagePosterPopularMovie = view.findViewById(R.id.imagePosterMovie);
        textTitlePopularMovie = view.findViewById(R.id.textTitlePopularMovie);
        imageEye = view.findViewById(R.id.imageEye);
        imageLike = view.findViewById(R.id.imageLike);
        imageBackPack = view.findViewById(R.id.imageBackPack);
        textOverview = view.findViewById(R.id.textOverview);
        textRating = view.findViewById(R.id.textRating);
        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        textVoteCount = view.findViewById(R.id.textVoteCount);
        textActorsRecyclerView = view.findViewById(R.id.textActorsRecyclerView);
        textMoviesRecyclerView = view.findViewById(R.id.textMoviesRecyclerView);
        textCountry = view.findViewById(R.id.textCountry);
        textCategories = view.findViewById(R.id.textCategories);
        textMinutes = view.findViewById(R.id.textMinutes);
        textImages = view.findViewById(R.id.textImages);

        viewYouTube = view.findViewById(R.id.viewYouTube);
        viewImages = view.findViewById(R.id.viewImages);
        viewActors = view.findViewById(R.id.viewActors);
        viewSimilar = view.findViewById(R.id.viewSimilar);

        webView = view.findViewById(R.id.webView);
        webView.setBackgroundColor(0);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idMovie");
            loadMovieDetails(idMovie);
        }

        ButtonUntil buttonUntil = new ButtonUntil(imageEye, imageLike, imageBackPack);
        textOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    textOverview.setMaxLines(5);
                    textOverview.setEllipsize(TextUtils.TruncateAt.END);
                } else {
                    textOverview.setMaxLines(Integer.MAX_VALUE);
                    textOverview.setEllipsize(null);
                }
                isExpanded = !isExpanded;
            }
        });

    }

    private void loadMovieDetails(int idMovie) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieTMDBApi tmdbApi = new MovieTMDBApi();
                Movie movieInfoTMDB = tmdbApi.getInfoMovie(idMovie);
                List<Actor> actors = tmdbApi.getActorsByIdMovie(idMovie);
                List<Movie> movies = tmdbApi.getSimilarMoviesById(idMovie);
                List<MovieImage> images = tmdbApi.getImagesByIdMovie(idMovie);
                String movieTrailerUrl = tmdbApi.getMovieTrailerUrl(idMovie);
                if (movieInfoTMDB != null) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setPosterAndTitle(movieInfoTMDB);
                            setMovieInfo(movieInfoTMDB);
                            setMovieRating(movieInfoTMDB);
                            setMovieTrailer(movieTrailerUrl);
                            setMovieImages(images);
                            setMovieActors(actors);
                            setMovieSimilar(movies);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
    }

    private void setPosterAndTitle(Movie movie) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(requireContext())
                .load(movie.getBackdropPath())
                .apply(requestOptions)
                .into(imageBackPopularMovie);
        Glide.with(requireContext())
                .load(movie.getPosterPath())
                .apply(requestOptions)
                .into(imagePosterPopularMovie);
        textTitlePopularMovie.setText(movie.getTitle());
    }

    @SuppressLint("SetTextI18n")
    private void setMovieInfo(Movie movie) {
        StringBuilder s = new StringBuilder();
        if (!movie.getProductionCountries().isEmpty()) {
            s.append(movie.getProductionCountries().get(0));
            for (int i = 1; i < movie.getProductionCountries().size(); i++) {
                s.append(", ");
                s.append(movie.getProductionCountries().get(i));
            }
            textCountry.setText(s);
        }
        StringBuilder genders = new StringBuilder();
        if (!movie.getReleaseDate().isEmpty())
            textMinutes.setText(movie.getReleaseDate().substring(0, 4) + ", " + movie.getRuntime() + " mins");
        if (!movie.getGenres().isEmpty()) {
            genders.append(movie.getGenres().get(0));
            for (int i = 1; i < movie.getGenres().size(); i++) {
                genders.append(", ");
                genders.append(movie.getGenres().get(i));
            }
            textCategories.setText(genders);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setMovieRating(Movie movie) {
        if (movie.getVoteAverage() != 0) {
            double rating = movie.getVoteAverage();
            textOverview.setText(movie.getOverview());
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            if (rating >= 8)
                textRating.setTextColor(Color.parseColor("#F1B36E"));
            else if (rating >= 5)
                textRating.setTextColor(Color.parseColor("#75FBE2"));
            else
                textRating.setTextColor(Color.parseColor("#E4416A"));
            textRating.setText(decimalFormat.format(rating));
            textVoteCount.setText("Votes: " + movie.getVoteCount());
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setMovieTrailer(String movieTrailerUrl) {
        if (movieTrailerUrl != null) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            String frameVideo = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"" + movieTrailerUrl +
                    "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            webView.loadData(frameVideo, "text/html", "utf-8");
        } else
            viewYouTube.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setMovieImages(List<MovieImage> images) {
        if (images.size() > 0) {
            textImages.setText("Images:");
            movieImagesAdapter = new ImagesAdapter(images);
            imagesRecyclerView.setAdapter(movieImagesAdapter);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            imagesRecyclerView.setLayoutManager(layoutManager2);
        } else
            viewImages.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setMovieActors(List<Actor> actors) {
        if (actors.size() > 0) {
            textActorsRecyclerView.setText("Actors:");
            actorsAdapter = new ActorsAdapter(actors);
            actorsRecyclerView.setAdapter(actorsAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            actorsRecyclerView.setLayoutManager(layoutManager);
            actorsAdapter.setOnActorClickListener(new ActorsAdapter.OnActorClickListener() {
                @Override
                public void onActorClick(int actorId) {
                    // Navigate to PersonFragment with actorId as an argument
                    Bundle args = new Bundle();
                    args.putInt("idPerson", actorId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_personFragment, args);
                }
            });
        } else
            viewActors.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setMovieSimilar(List<Movie> movies) {
        if (movies.size() > 0) {
            textMoviesRecyclerView.setText("Similar:");
            movieAdapter = new MovieAdapter(movies);
            moviesRecyclerView.setAdapter(movieAdapter);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            moviesRecyclerView.setLayoutManager(layoutManager1);
            movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
                @Override
                public void onMovieClick(int movieId) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movieId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_self, args);
                }
            });
        } else
            viewSimilar.setVisibility(View.GONE);
    }

}
