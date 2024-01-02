package com.example.moviepocketandroid.ui.user.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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

    private View itemRecyclerViewMovie0, itemRecyclerViewMovie1, itemRecyclerViewMovie2, view;
    private View textView0, textView1, textView2;
    private TextView favoriteTextView, toWatchTextView, watchedTextView, textViewUsername;
    private RecyclerView movieFavoriteRecyclerView, movieToWatchRecyclerView, movieWatchedRecyclerView;
    private ImageButton imageButtonSettings;
    private User user;
    private ImageView imageViewAvatar;


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

        // Проверка аутентификации в отдельном потоке
        new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean isAuthentication = MPAuthenticationApi.checkAuth();

                // Если не прошла аутентификация и фрагмент прикреплен к активности
                if (!isAuthentication && isAdded()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Переход к LoginFragment с использованием NavController
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_userFragment_to_loginFragment);
                        }
                    });
                }
            }
        }).start();

        // Инициализация элементов интерфейса
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

        this.view = view;

        // Восстановление данных после изменения конфигурации
        if (savedInstanceState != null) {
            Serializable toWatchSerializable = savedInstanceState.getSerializable("toWatchKey");
            Serializable favoritesSerializable = savedInstanceState.getSerializable("favoritesKey");
            Serializable watchedSerializable = savedInstanceState.getSerializable("watchedKey");
            Serializable userSerializable = savedInstanceState.getSerializable("user");

            // Проверка на null перед использованием
            if (toWatchSerializable != null) {
                toWatch = Collections.checkedList((List<Movie>) toWatchSerializable, Movie.class);
            }

            if (favoritesSerializable != null) {
                favorites = Collections.checkedList((List<Movie>) favoritesSerializable, Movie.class);
            }

            if (watchedSerializable != null) {
                watched = Collections.checkedList((List<Movie>) watchedSerializable, Movie.class);
            }

            // Установка информации, если пользователь уже аутентифицирован
            if (userSerializable != null) {
                user = (User) userSerializable;
                setInfo();
            } else {
                loadMovieDet();
            }
        } else {
            loadMovieDet();
        }
    }


    private final Object lock = new Object();

    private void loadMovieDet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
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
            }
        }).start();
    }


    @SuppressLint("SetTextI18n")
    private void setInfo() {
        synchronized (lock) {
            if (user != null) {
                textViewUsername.setText(user.getUsername());
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

                if (user.getAvatar() != null) {
                    imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
                    RequestOptions requestOptions = new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(view.getContext())
                            .load(user.getAvatar())
                            .apply(requestOptions)
                            .into(imageViewAvatar);
                }

            }
        }
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
        if (user != null) {
            outState.putSerializable("toWatchKey", (Serializable) toWatch);
            outState.putSerializable("favoritesKey", (Serializable) favorites);
            outState.putSerializable("watchedKey", (Serializable) watched);
            outState.putSerializable("user", user);
        }
    }

}