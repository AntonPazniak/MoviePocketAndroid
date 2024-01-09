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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.PostAdapter;
import com.example.moviepocketandroid.api.MP.MPAssessmentApi;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.MP.MPUserApi;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.api.models.user.User;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class UserFragment extends Fragment {
    private UserViewModel viewModel;
    private List<Movie> toWatch;
    private List<Movie> favorites;
    private List<Movie> watched;
    private View view;
    private TextView favoriteTextView, toWatchTextView, watchedTextView, textViewUsername;
    private RecyclerView movieFavoriteRecyclerView, movieToWatchRecyclerView, movieWatchedRecyclerView;
    private ImageButton imageButtonSettings;
    private User user;
    private ImageView imageViewAvatar;
    private RecyclerView recyclerViewList, recyclerViewPost;
    private TextView textViewList, textViewPost;
    private List<MovieList> lists;
    private List<Post> posts;


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

        View view0 = view.findViewById(R.id.view0);
        View view1 = view.findViewById(R.id.view1);
        View view2 = view.findViewById(R.id.view2);

        movieToWatchRecyclerView = view0.findViewById(R.id.recyclerView);
        movieFavoriteRecyclerView = view1.findViewById(R.id.recyclerView);
        movieWatchedRecyclerView = view2.findViewById(R.id.recyclerView);

        toWatchTextView = view0.findViewById(R.id.textView);
        favoriteTextView = view1.findViewById(R.id.textView);
        watchedTextView = view2.findViewById(R.id.textView);
        textViewUsername = view.findViewById(R.id.textViewUsername);

        imageButtonSettings = view.findViewById(R.id.imageButtonSettings);

        View listView = view.findViewById(R.id.listView);
        View postView = view.findViewById(R.id.postView);

        textViewList = listView.findViewById(R.id.textView);
        recyclerViewList = listView.findViewById(R.id.recyclerView);

        textViewPost = postView.findViewById(R.id.textView);
        recyclerViewPost = postView.findViewById(R.id.recyclerView);


        this.view = view;

        try {
            if (savedInstanceState != null) {
                Serializable toWatchSerializable = savedInstanceState.getSerializable("toWatchKey");
                Serializable favoritesSerializable = savedInstanceState.getSerializable("favoritesKey");
                Serializable watchedSerializable = savedInstanceState.getSerializable("watchedKey");
                Serializable listSerializable = savedInstanceState.getSerializable("lists");
                Serializable postSerializable = savedInstanceState.getSerializable("posts");
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

                if (watchedSerializable != null) {
                    lists = Collections.checkedList((List<MovieList>) listSerializable, MovieList.class);
                }

                if (watchedSerializable != null) {
                    posts = Collections.checkedList((List<Post>) postSerializable, Post.class);
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
        } catch (NullPointerException e) {
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
                    lists = MPListApi.getAllMyList();
                    posts = MPPostApi.getAllMyPost();

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

                toWatchTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putString("ToWatch", "ToWatch");
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userFragment_to_movieListFragment, args);
                    }
                });

                favoriteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putString("Favorite", "Favorite");
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userFragment_to_movieListFragment, args);
                    }
                });

                watchedTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putString("Watched", "Watched");
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userFragment_to_movieListFragment, args);
                    }
                });
                setMyLists();
                setMyPosts();
            }
        }
    }

    private void setMovies(RecyclerView recyclerView, List<Movie> movies) {
        Collections.reverse(movies);
        if (movies.size() > 5) {
            movies = movies.subList(0, 5);
        }
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


    @SuppressLint("SetTextI18n")
    private void setMyLists() {
        try {
            textViewList.setText("All my Movie Lists");
            if (lists != null && !lists.isEmpty()) {
                textViewList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putInt("my", 1);
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userFragment_to_listAllFragment, args);
                    }
                });
                ListAdapter listAdapter = new ListAdapter(lists);
                recyclerViewList.setAdapter(listAdapter);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerViewList.setLayoutManager(layoutManager1);
                listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int idList) {
                        Bundle args = new Bundle();
                        args.putInt("idList", idList);
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userPageFragment_to_listFragment, args);
                    }
                });
            }

        } catch (IllegalStateException e) {
            onDestroy();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setMyPosts() {
        try {
            textViewPost.setText("All my Movie Posts");
            if (posts != null && !posts.isEmpty()) {
                textViewPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle args = new Bundle();
                        args.putInt("my", 1);
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userFragment_to_postAllFragment, args);
                    }
                });
                PostAdapter postAdapter = new PostAdapter(posts);
                recyclerViewPost.setAdapter(postAdapter);
                GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false);
                recyclerViewPost.setLayoutManager(layoutManager);

                postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int idPost) {
                        Bundle args = new Bundle();
                        args.putInt("idPost", idPost);
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                        navController.navigate(R.id.action_userFragment_to_postFragment, args);
                    }
                });
            }

        } catch (IllegalStateException e) {
            onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (user != null) {
            outState.putSerializable("toWatchKey", (Serializable) toWatch);
            outState.putSerializable("favoritesKey", (Serializable) favorites);
            outState.putSerializable("watchedKey", (Serializable) watched);
            outState.putSerializable("lists", (Serializable) lists);
            outState.putSerializable("posts", (Serializable) posts);
            outState.putSerializable("user", user);
        }
    }

}