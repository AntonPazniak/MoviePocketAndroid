package com.example.moviepocketandroid.ui.movie;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.moviepocketandroid.adapter.ReviewAdapter;
import com.example.moviepocketandroid.api.MP.MPAuthenticationAPI;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.MovieImage;
import com.example.moviepocketandroid.api.models.review.Review;
import com.example.moviepocketandroid.util.ButtonUntil;

import java.text.DecimalFormat;
import java.util.List;

public class MovieFragment extends Fragment {

    private MovieViewModel mViewModel;
    private ImageView imageBackPopularMovie, imagePosterPopularMovie;
    private TextView textTitlePopularMovie;
    private ImageView imageEye, imageLike, imageBackPack, imageBinoculars;
    private TextView textRating, textVoteCount, textOverview, textImages, textViewOverview;
    private TextView textActorsRecyclerView, textMoviesRecyclerView;
    private TextView textCountry, textCategories, textMinutes;
    private ActorsAdapter actorsAdapter;
    private MovieAdapter movieAdapter;
    private ImagesAdapter movieImagesAdapter;
    private ReviewAdapter reviewAdapter;
    private View viewYouTube, viewImages, viewActors, viewSimilar, viewOverview;
    private boolean isExpanded = false;
    private RecyclerView actorsRecyclerView, moviesRecyclerView, imagesRecyclerView, reviewRecyclerView;
    private WebView webView;
    private Button button2, button;


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
        imageBinoculars = view.findViewById(R.id.imageBinoculars);

        textOverview = view.findViewById(R.id.textOverview);
        textRating = view.findViewById(R.id.textRating);

        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        reviewRecyclerView = view.findViewById(R.id.recyclerViewReview);

        textVoteCount = view.findViewById(R.id.textVoteCount);
        textActorsRecyclerView = view.findViewById(R.id.textActorsRecyclerView);
        textMoviesRecyclerView = view.findViewById(R.id.textMoviesRecyclerView);
        textCountry = view.findViewById(R.id.textCountry);
        textCategories = view.findViewById(R.id.textCategories);
        textMinutes = view.findViewById(R.id.textMinutes);
        textImages = view.findViewById(R.id.textImages);
        textViewOverview = view.findViewById(R.id.textViewOverview);

        viewYouTube = view.findViewById(R.id.viewYouTube);
        viewImages = view.findViewById(R.id.viewImages);
        viewActors = view.findViewById(R.id.viewActors);
        viewSimilar = view.findViewById(R.id.viewSimilar);
        viewOverview = view.findViewById(R.id.viewOverview);

        button2 = view.findViewById(R.id.button2);
        button = view.findViewById(R.id.button);

        webView = view.findViewById(R.id.webView);
        webView.setBackgroundColor(0);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idMovie");
            loadMovieDetails(idMovie);
        }


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
                TMDBApi tmdbApi = new TMDBApi();
                Movie movieInfoTMDB = tmdbApi.getInfoMovie(idMovie);
                List<Actor> actors = tmdbApi.getActorsByIdMovie(idMovie);
                List<Movie> movies = tmdbApi.getSimilarMoviesById(idMovie);
                List<MovieImage> images = tmdbApi.getImagesByIdMovie(idMovie);
                String movieTrailerUrl = tmdbApi.getMovieTrailerUrl(idMovie);
                Boolean isAuthentication = MPAuthenticationAPI.checkAuth();


                MPReviewApi mpApi = new MPReviewApi();
                List<Review> reviews = mpApi.getReviewAllByIdMovie(idMovie);

                if (movieInfoTMDB != null && isAdded()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isAuthentication) {
                                setButtons(movieInfoTMDB);
                                button2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Bundle args = new Bundle();
                                        args.putInt("idMovie", movieInfoTMDB.getId());

                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_movieFragment_to_newReviewFragment, args);
                                    }
                                });
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Bundle args = new Bundle();
                                        args.putInt("idMovie", movieInfoTMDB.getId());

                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_movieFragment_to_allReviewFragment, args);
                                    }
                                });
                            }
                            setPosterAndTitle(movieInfoTMDB);
                            setMovieInfo(movieInfoTMDB);
                            setMovieRating(movieInfoTMDB);
                            setMovieTrailer(movieTrailerUrl);
                            setMovieImages(images);
                            setMovieActors(actors);
                            setMovieSimilar(movies);
                            setMovieReview(reviews);
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

    private void setButtons(Movie movie) {
        ButtonUntil buttonUntil;
        if (movie.getStatus().equals("Planned")) {
            imageBinoculars.setVisibility(View.VISIBLE);
            imageBackPack.setVisibility(View.VISIBLE);
            imageEye.setVisibility(View.GONE);
            imageLike.setVisibility(View.GONE);
            buttonUntil = new ButtonUntil(imageBackPack, imageBinoculars, movie.getId());
        } else {
            imageEye.setVisibility(View.VISIBLE);
            imageLike.setVisibility(View.VISIBLE);
            imageBackPack.setVisibility(View.VISIBLE);
            imageBinoculars.setVisibility(View.GONE);
            buttonUntil = new ButtonUntil(imageEye, imageLike, imageBackPack, movie.getId());
        }
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
            if (movie.getId() > 0) {
                textMinutes.setText(movie.getReleaseDate().substring(0, 4) + ", " + movie.getRuntime() + " mins");
            } else {
                textMinutes.setText(movie.getReleaseDate().substring(0, 4) + ", Seasons: " + movie.getNumberOfEpisodes() + " Episodes: " + movie.getNumberOfEpisodes());
            }
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
            if(!movie.getOverview().equals("null")) {
                textViewOverview.setText("Description");
                textOverview.setText(movie.getOverview());
            }else
                viewOverview.setVisibility(View.GONE);
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

    private void setMovieReview(List<Review> reviews) {
        if (reviews.size() > 0) {
            reviewAdapter = new ReviewAdapter(reviews);
            reviewRecyclerView.setAdapter(reviewAdapter);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            reviewRecyclerView.setLayoutManager(layoutManager2);
            reviewAdapter.setOnReviewClickListener(new ReviewAdapter.OnReviewClickListener() {
                @Override
                public void onReviewClick(int reviewId) {
                    Bundle args = new Bundle();
                    args.putInt("idReview", reviewId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_detailReviewFragment, args);
                }
            });
        }
    }
}
