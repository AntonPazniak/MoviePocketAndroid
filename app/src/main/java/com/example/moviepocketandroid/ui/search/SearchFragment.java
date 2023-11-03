package com.example.moviepocketandroid.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorsAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;

import java.util.List;

public class SearchFragment extends Fragment {

    private MovieAdapter movieAdapter, tvAdapter;
    private ActorsAdapter actorsAdapter;
    private RecyclerView moviesRecyclerView, actorsRecyclerView, tvRecyclerView;
    private TextView textActorsRecyclerView, textMoviesRecyclerView, textTVRecyclerView;
    private ImageButton buttonSearch;
    private EditText editTextSearch;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView);
        tvRecyclerView = view.findViewById(R.id.tvRecyclerView);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        editTextSearch = view.findViewById(R.id.editTextSearch);
        textActorsRecyclerView = view.findViewById(R.id.textActorsRecyclerView);
        textMoviesRecyclerView = view.findViewById(R.id.textMoviesRecyclerView);
        textTVRecyclerView = view.findViewById(R.id.textTVRecyclerView);

        loadMovieDetails();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = String.valueOf(editTextSearch.getText());
                if (!query.isEmpty()) {
                    Bundle args = new Bundle();
                    args.putString("query", query);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_navigation_search_to_searchResultsFragment, args);
                }
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    private void loadMovieDetails() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TMDBApi tmdbApi = new TMDBApi();
                List<Movie> movies = tmdbApi.getPopularMovies();
                List<Actor> actors = tmdbApi.getPopularActors();
                List<Movie> tvSeries = tmdbApi.getPopularTVs();
                requireActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {

                        setPopularMovie(movies);
                        setPopularTVs(tvSeries);
                        setActors(actors);

                    }
                });
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    private void setPopularMovie(List<Movie> movies) {
        if (movies != null) {
            textMoviesRecyclerView.setText("Popular movies:");
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
                    navController.navigate(R.id.action_navigation_search_to_movieFragment, args);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void setPopularTVs(List<Movie> tvSeries) {
        if (tvSeries != null) {
            textTVRecyclerView.setText("Popular TV Series:");
            tvAdapter = new MovieAdapter(tvSeries);
            tvRecyclerView.setAdapter(tvAdapter);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            tvRecyclerView.setLayoutManager(layoutManager2);
            tvAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
                @Override
                public void onMovieClick(int movieId) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movieId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_navigation_search_to_movieFragment, args);
                }
            });
        }
    }

    private void setActors(List<Actor> actors) {
        if (actors != null) {
            textActorsRecyclerView.setText("Popular actors:");
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
                    navController.navigate(R.id.action_navigation_search_to_personFragment, args);
                }
            });
        }
    }

}