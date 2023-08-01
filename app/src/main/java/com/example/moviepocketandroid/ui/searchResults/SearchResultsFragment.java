package com.example.moviepocketandroid.ui.searchResults;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import android.widget.TextView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorSearchAdapter;
import com.example.moviepocketandroid.adapter.SearchAdapter;
import com.example.moviepocketandroid.adapter.TVSeriesSearchAdapter;
import com.example.moviepocketandroid.api.TMDB.ActorTMDBApi;
import com.example.moviepocketandroid.api.TMDB.TVSeriesTMDBApi;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.TMDB.MovieTMDBApi;
import com.example.moviepocketandroid.api.models.tv.TVSeason;
import com.example.moviepocketandroid.api.models.tv.TVSeries;

import java.util.List;

public class SearchResultsFragment extends Fragment {


    private SearchAdapter searchAdapter;
    private ActorSearchAdapter actorSearchAdapter;
    private TVSeriesSearchAdapter tvSeriesSearchAdapter;
    private RecyclerView searchRecyclerView;
    private TextView textQuery;
    private TextView textMovies;
    private TextView textTVs;
    private TextView textPersons;


    private List<Movie> movies;
    private List<Actor> actors;
    private List<TVSeries> tvSeries;

    private boolean isMovies = true;
    private boolean isTVs = false;
    private boolean isPersons = false;

    public static SearchResultsFragment newInstance() {
        return new SearchResultsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        textQuery = view.findViewById(R.id.textQuery);
        textMovies = view.findViewById(R.id.textMovies);
        textTVs = view.findViewById(R.id.textTVs);
        textPersons = view.findViewById(R.id.textPersons);

        Bundle args = getArguments();
        if (args != null) {
            String query = args.getString("query");
            loadMovieDetails(query);
            textQuery.setText("Results: " + query);
        }


    }

    private void loadMovieDetails(String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieTMDBApi movieTMDBApi = new MovieTMDBApi();
                TVSeriesTMDBApi tvSeriesTMDBApi = new TVSeriesTMDBApi();
                ActorTMDBApi actorTMDBApi = new ActorTMDBApi();
                movies = movieTMDBApi.getSearchResults(query);
                tvSeries = tvSeriesTMDBApi.getSearchResults(query);
                actors = actorTMDBApi.getSearchResults(query);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (movies != null) {
                            searchAdapter = new SearchAdapter(movies);
                            searchRecyclerView.setAdapter(searchAdapter);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                            searchRecyclerView.setLayoutManager(layoutManager2);
                            searchAdapter.setOnMovieClickListener(new SearchAdapter.OnMovieClickListener() {
                                @Override
                                public void onMovieClick(int movieId) {
                                    Bundle args = new Bundle();
                                    args.putInt("idMovie", movieId);

                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigate(R.id.action_searchResultsFragment_to_movieFragment, args);
                                }
                            });
                        }
                        textMovies.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!isMovies) {
                                    isMovies = true;
                                    isPersons = false;
                                    isTVs = false;
                                    textMovies.setTextColor(Color.parseColor("#E4416A"));
                                    textTVs.setTextColor(Color.parseColor("#75FBE2"));
                                    textPersons.setTextColor(Color.parseColor("#75FBE2"));
                                    setMovie();

                                }
                            }
                        });

                        textTVs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!isTVs) {
                                    isTVs = true;
                                    isPersons = false;
                                    isMovies = false;
                                    textMovies.setTextColor(Color.parseColor("#75FBE2"));
                                    textTVs.setTextColor(Color.parseColor("#E4416A"));
                                    textPersons.setTextColor(Color.parseColor("#75FBE2"));
                                    setTV();

                                }
                            }
                        });

                        textPersons.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!isPersons) {
                                    isPersons = true;
                                    isTVs = false;
                                    isMovies = false;
                                    textMovies.setTextColor(Color.parseColor("#75FBE2"));
                                    textTVs.setTextColor(Color.parseColor("#75FBE2"));
                                    textPersons.setTextColor(Color.parseColor("#E4416A"));
                                    setPerson();
                                }
                            }
                        });

                    }
                });
            }
        }).start();
    }

    private void setMovie() {
        searchAdapter = new SearchAdapter(movies);
        searchRecyclerView.setAdapter(searchAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager2);
        searchAdapter.setOnMovieClickListener(new SearchAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(int movieId) {
                Bundle args = new Bundle();
                args.putInt("idMovie", movieId);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_searchResultsFragment_to_movieFragment, args);
            }
        });
    }

    private void setTV() {
        tvSeriesSearchAdapter = new TVSeriesSearchAdapter(tvSeries);
        searchRecyclerView.setAdapter(tvSeriesSearchAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager2);
        tvSeriesSearchAdapter.setOnTVSeriesClickListener(new TVSeriesSearchAdapter.OnTVSeriesClickListener() {
            @Override
            public void onTVSeriesClick(int idTV) {
                Bundle args = new Bundle();
                args.putInt("idTV", idTV);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_searchResultsFragment_to_TVSeriesFragment, args);
            }
        });
    }

    private void setPerson() {
        actorSearchAdapter = new ActorSearchAdapter(actors);
        searchRecyclerView.setAdapter(actorSearchAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager2);
        actorSearchAdapter.setOnActorClickListener(new ActorSearchAdapter.OnActorClickListener() {
            @Override
            public void onActorClick(int actorId) {
                // Navigate to PersonFragment with actorId as an argument
                Bundle args = new Bundle();
                args.putInt("idPerson", actorId);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_searchResultsFragment_to_personFragment, args);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}