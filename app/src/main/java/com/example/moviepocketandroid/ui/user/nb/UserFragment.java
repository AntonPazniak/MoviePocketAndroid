package com.example.moviepocketandroid.ui.user.nb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.api.MP.MPAssessmentApi;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPUserApi;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.user.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class UserFragment extends Fragment {
    private UserViewModel viewModel;
    private List<Movie> toWatch;
    private List<Movie> favorites;
    private List<Movie> watched;

    private View itemRecyclerViewMovie0, itemRecyclerViewMovie1, itemRecyclerViewMovie2;
    private View textView0, textView1, textView2;
    private TextView favoriteTextView, toWatchTextView, watchedTextView, textViewUsername;
    private RecyclerView movieFavoriteRecyclerView, movieToWatchRecyclerView, movieWatchedRecyclerView;
    private ImageButton imageButtonSettings;
    private User user;


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
                Boolean isAuthentication = MPAuthenticationApi.checkAuth();

                if (!isAuthentication && isAdded()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_userFragment_to_loginFragment);
                        }
                    });
                }
            }
        }).start();

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
        textViewUsername = view.findViewById(R.id.textViewUsername);
        imageButtonSettings = view.findViewById(R.id.imageButtonSettings);

        if (savedInstanceState != null) {
            toWatch = Collections.checkedList(
                    (List<Movie>) savedInstanceState.getSerializable("toWatchKey"), Movie.class);

            favorites = Collections.checkedList(
                    (List<Movie>) savedInstanceState.getSerializable("favoritesKey"), Movie.class);

            watched = Collections.checkedList(
                    (List<Movie>) savedInstanceState.getSerializable("watchedKey"), Movie.class);

            user = (User) savedInstanceState.getSerializable("user");

            if (user != null)
                setInfo();
            else
                loadMovieDet();
        } else
            loadMovieDet();
    }

    private void loadMovieDet() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                favorites = MPAssessmentApi.getAllFavoriteMovie();
                toWatch = MPAssessmentApi.getAllToWatchMovie();
                watched = MPAssessmentApi.getAllWatchedMovie();
                user = MPUserApi.getUserInfo();

                if (isAdded()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            setInfo();
                        }
                    });

                }

            }
        }).start();
    }


    @SuppressLint("SetTextI18n")
    private void setInfo() {
        textViewUsername.setText(user.getUsername());
        toWatchTextView.setText("Watchlist");
        setMovies(movieToWatchRecyclerView, toWatch);
        favoriteTextView.setText("Favorite movie");
        setMovies(movieFavoriteRecyclerView, favorites);
        watchedTextView.setText("Watched movie");
        setMovies(movieWatchedRecyclerView, watched);

        textViewUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                assert user != null;
                args.putString("username", user.getUsername());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_userFragment_to_userPageFragment, args);
            }

        });

        toWatchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("watchedListKey", (Serializable) watched);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_userFragment_to_movieListFragment, args);
            }

        });

        favoriteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("watchedListKey", (Serializable) favorites);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_userFragment_to_movieListFragment, args);
            }

        });

        watchedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("watchedListKey", (Serializable) toWatch);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_userFragment_to_movieListFragment, args);
            }

        });


        imageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_userFragment_to_userEditFragment);
            }

        });

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("toWatchKey", (Serializable) toWatch);
        outState.putSerializable("favoritesKey", (Serializable) favorites);
        outState.putSerializable("watchedKey", (Serializable) watched);
        outState.putSerializable("user", user);
    }

}