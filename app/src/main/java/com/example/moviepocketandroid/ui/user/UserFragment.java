package com.example.moviepocketandroid.ui.user;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.MovieTokAdapter;
import com.example.moviepocketandroid.adapter.NowPlayingMovieAdapter;
import com.example.moviepocketandroid.api.MP.MPAssessmentAPI;
import com.example.moviepocketandroid.api.MP.MPAuthenticationAPI;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private UserViewModel viewModel;
    private List<Movie> toWatch;
    private List<Movie> favorites;
    private List<Movie> watched;

    private View itemRecyclerViewMovie0, itemRecyclerViewMovie1, itemRecyclerViewMovie2;
    private View textView0, textView1, textView2;
    private TextView favoriteTextView, toWatchTextView, watchedTextView;
    private RecyclerView movieFavoriteRecyclerView, movieToWatchRecyclerView, movieWatchedRecyclerView;


    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean isAuthentication = MPAuthenticationAPI.checkAuth();

                if (!isAuthentication && isAdded()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_userFragment_to_loginFragment);
                        }
                    });
                } else {

                    itemRecyclerViewMovie0 = view.findViewById(R.id.itemRecyclerViewMovie0);
                    itemRecyclerViewMovie1 = view.findViewById(R.id.itemRecyclerViewMovie1);
                    itemRecyclerViewMovie2 = view.findViewById(R.id.itemRecyclerViewMovie2);

                    movieToWatchRecyclerView = itemRecyclerViewMovie0.findViewById(R.id.moviesRecyclerView);
                    movieFavoriteRecyclerView = itemRecyclerViewMovie1.findViewById(R.id.moviesRecyclerView);
                    movieWatchedRecyclerView = itemRecyclerViewMovie2.findViewById(R.id.moviesRecyclerView);

                    textView0 = view.findViewById(R.id.textView0);
                    textView1 = view.findViewById(R.id.textView1);
                    textView2 = view.findViewById(R.id.textView2);

                    toWatchTextView = textView0.findViewById(R.id.textView);
                    favoriteTextView = textView1.findViewById(R.id.textView);
                    watchedTextView = textView2.findViewById(R.id.textView);

                    MPAssessmentAPI mpAssessmentAPI = new MPAssessmentAPI();
                    TMDBApi tmdbApi = new TMDBApi();

                    List<Movie> favorites = new ArrayList<>();
                    List<Movie> toWatch = new ArrayList<>();
                    List<Movie> watched = new ArrayList<>();

                    int[] arr = mpAssessmentAPI.getAllFavoriteMovie();
                    int numMoviesToDisplay = Math.min(6, arr.length);
                    for (int i = arr.length - 1; i >= arr.length - numMoviesToDisplay; i--) {
                        Movie movie = tmdbApi.getInfoMovie(arr[i]);
                        if (movie != null) {
                            favorites.add(movie);
                        }
                    }

                    arr = mpAssessmentAPI.getAllToWatchMovie();
                    numMoviesToDisplay = Math.min(6, arr.length);
                    for (int i = arr.length - 1; i >= arr.length - numMoviesToDisplay; i--) {
                        Movie movie = tmdbApi.getInfoMovie(arr[i]);
                        if (movie != null) {
                            toWatch.add(movie);
                        }
                    }

                    arr = mpAssessmentAPI.getAllWatchedMovie();
                    numMoviesToDisplay = Math.min(6, arr.length);
                    for (int i = arr.length - 1; i >= arr.length - numMoviesToDisplay; i--) {
                        Movie movie = tmdbApi.getInfoMovie(arr[i]);
                        if (movie != null) {
                            watched.add(movie);
                        }
                    }

                    if (isAdded()) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                toWatchTextView.setText("To watch movie");
                                setMovies(movieToWatchRecyclerView, toWatch);
                                favoriteTextView.setText("Favorite movie");
                                setMovies(movieFavoriteRecyclerView, favorites);
                                watchedTextView.setText("Watched movie");
                                setMovies(movieWatchedRecyclerView, watched);
                            }
                        });
                    }
                }
            }
        }).start();


    }

    private void setMovies(RecyclerView recyclerView, List<Movie> movies) {
        MovieAdapter movieAdapter = new MovieAdapter(movies);
        recyclerView.setAdapter(movieAdapter);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager1);
        movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(int movieId) {
                Bundle args = new Bundle();
                args.putInt("idMovie", movieId);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_userFragment_to_movieFragment, args);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}