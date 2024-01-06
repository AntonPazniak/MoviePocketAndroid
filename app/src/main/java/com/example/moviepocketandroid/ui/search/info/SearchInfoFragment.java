package com.example.moviepocketandroid.ui.search.info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorsAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.person.Person;

import java.io.Serializable;
import java.util.List;

public class SearchInfoFragment extends Fragment {

    private MovieAdapter movieAdapter, tvAdapter;
    private ActorsAdapter actorsAdapter;
    private RecyclerView moviesRecyclerView, actorsRecyclerView, tvRecyclerView;
    private TextView textActorsRecyclerView, textMoviesRecyclerView, textTVRecyclerView;
    private List<Movie> movies;
    private List<Person> actors;
    private List<Movie> tvSeries;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View view0 = view.findViewById(R.id.view0);
        View view1 = view.findViewById(R.id.view1);
        View view2 = view.findViewById(R.id.view2);
        moviesRecyclerView = view0.findViewById(R.id.recyclerView);
        actorsRecyclerView = view1.findViewById(R.id.recyclerView);
        tvRecyclerView = view2.findViewById(R.id.recyclerView);
        textMoviesRecyclerView = view0.findViewById(R.id.textView);
        textActorsRecyclerView = view1.findViewById(R.id.textView);
        textTVRecyclerView = view2.findViewById(R.id.textView);

        loadMovieDetails();


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
                movies = TMDBApi.getPopularMovies();
                actors = TMDBApi.getPopularActors();
                tvSeries = TMDBApi.getPopularTVs();
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

    private void setInfo() {
        setPopularMovie(movies);
        setPopularTVs(tvSeries);
        setActors(actors);
    }

    private void setPopularMovie(List<Movie> movies) {
        if (movies != null) {
            textMoviesRecyclerView.setText(R.string.popular_movies);
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

    private void setPopularTVs(List<Movie> tvSeries) {
        if (tvSeries != null) {
            textTVRecyclerView.setText(R.string.popular_tv_series);
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

    private void setActors(List<Person> actors) {
        if (actors != null) {
            textActorsRecyclerView.setText(R.string.popular_actors);
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


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("movies", (Serializable) movies);
        outState.putSerializable("actors", (Serializable) actors);
        outState.putSerializable("tvSeries", (Serializable) tvSeries);
    }

}