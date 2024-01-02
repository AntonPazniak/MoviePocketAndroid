package com.example.moviepocketandroid.ui.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.api.MP.MPAssessmentApi;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.util.Collections;
import java.util.List;

public class MovieListFragment extends Fragment {

    private MovieListViewModel mViewModel;
    private RecyclerView recyclerViewList;
    private List<Movie> movies;
    String favorite;
    String toWatch;
    String watched;
    private TextView textViewTitle;

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewList = view.findViewById(R.id.recyclerViewList);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MPAuthenticationApi.checkAuth()) {

                    Bundle args = getArguments();
                    if (args != null) {
                        favorite = args.getString("Favorite", null);
                        toWatch = args.getString("ToWatch", null);
                        watched = args.getString("Watched", null);
                        if (favorite != null && favorite.equals("Favorite")) {
                            setMoviesFavorite();
                        } else if (toWatch != null && toWatch.equals("ToWatch")) {
                            setMoviesToWatch();
                        } else if (watched != null && watched.equals("Watched")) {
                            setMoviesWatched();
                        }
                    }
                } else {
                    if (isAdded() && getContext() != null) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigate(R.id.action_listFragment_to_loginFragment);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void setMoviesFavorite() {
        movies = MPAssessmentApi.getAllFavoriteMovie();
        setMovie();
    }

    private void setMoviesWatched() {
        movies = MPAssessmentApi.getAllWatchedMovie();
        setMovie();
    }

    private void setMoviesToWatch() {
        movies = MPAssessmentApi.getAllToWatchMovie();
        setMovie();
    }

    private void setMovie() {
        if (isAdded() && getContext() != null) {
            if (movies != null && !movies.isEmpty()) {
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        if (favorite != null && favorite.equals("Favorite")) {
                            textViewTitle.setText("Favorite");
                        } else if (toWatch != null && toWatch.equals("ToWatch")) {
                            textViewTitle.setText("ToWatch");
                        } else if (watched != null && watched.equals("Watched")) {
                            textViewTitle.setText("Watched");
                        }
                        Collections.reverse(movies);
                        MovieAdapter movieAdapter = new MovieAdapter(movies);
                        recyclerViewList.setAdapter(movieAdapter);

                        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false);
                        recyclerViewList.setLayoutManager(layoutManager);

                        movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
                            @Override
                            public void onMovieClick(int movieId) {
                                Bundle args = new Bundle();
                                args.putInt("idMovie", movieId);

                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigate(R.id.action_movieListFragment_to_movieFragment, args);
                            }
                        });
                    }
                });
            }
        }
    }

}