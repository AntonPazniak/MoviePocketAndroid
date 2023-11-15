package com.example.moviepocketandroid.ui.movie.list;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.MovieList;
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {

    private MovieListViewModel mViewModel;
    private TextView textViewTitle, textViewContent, textViewUsername, textViewDate;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerViewList;
    private LinearLayout linearLayoutAuthor;
    private ImageView imageViewAvatar;

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

        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewContent = view.findViewById(R.id.textViewContent);
        recyclerViewList = view.findViewById(R.id.recyclerViewList);
        linearLayoutAuthor = view.findViewById(R.id.linearLayoutAuthor);
        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewDate = view.findViewById(R.id.textViewDate);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);

        Bundle args = getArguments();
        if (args != null) {
            int idList = args.getInt("idList", -1);
            if (idList > 0) {
                loadListInf(idList);
            } else {
                int[] movies = args.getIntArray("movies");
                loadMovieDetails(movies);
            }
        }
    }

    private void loadListInf(int idList) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                MovieList movieList = (MovieList) MPListApi.getListById(idList);
                if (movieList != null) {
                    List<Movie> movies = new ArrayList<>();
                    for (int i : movieList.getIdMovies())
                        movies.add(TMDBApi.getInfoMovie(i));

                    if (isAdded()) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setMovie(movies);
                                setListInf(movieList);
                            }
                        });
                    }
                }
            }
        }).start();

    }

    private void loadMovieDetails(int[] moviesArr) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Movie> movies = loadMovie(moviesArr);

                if (isAdded()) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            setMovie(movies);
                        }
                    });
                }
            }
        }).start();
    }

    private List<Movie> loadMovie(int[] moviesArr) {
        List<Movie> movies = new ArrayList<>();
        for (int j : moviesArr) {
            Movie movie = TMDBApi.getInfoMovie(j);
            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }

    @SuppressLint("SetTextI18n")
    private void setMovie(List<Movie> movies) {
        if (movies.size() > 0) {
            movieAdapter = new MovieAdapter(movies);
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
    }

    private void setListInf(MovieList movieList) {
        linearLayoutAuthor.setVisibility(View.VISIBLE);
        textViewUsername.setText(movieList.getUsername());
        textViewDate.setText(movieList.getCreate().toString());
        textViewTitle.setText(movieList.getTitle());
        textViewContent.setText(movieList.getContent());
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("username", movieList.getUsername());
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_movieListFragment_to_userPageFragment, args);
            }

        });

    }


}