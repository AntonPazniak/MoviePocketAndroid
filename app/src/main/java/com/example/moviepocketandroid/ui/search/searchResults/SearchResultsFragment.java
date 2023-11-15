package com.example.moviepocketandroid.ui.search.searchResults;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.example.moviepocketandroid.adapter.search.ActorSearchAdapter;
import com.example.moviepocketandroid.adapter.search.SearchAdapter;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.Person;
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.io.Serializable;
import java.util.List;

public class SearchResultsFragment extends Fragment {

    private SearchAdapter searchAdapter;
    private ActorSearchAdapter actorSearchAdapter;
    private SearchAdapter tvSeriesSearchAdapter;
    private RecyclerView searchRecyclerView;
    private TextView textQuery;
    private TextView textMovies, textTVs, textPersons;

    private List<Movie> movies;
    private List<Person> actors;
    private List<Movie> tvSeries;

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


        if (savedInstanceState != null) {
            movies = (List<Movie>) savedInstanceState.getSerializable("movies");
            actors = (List<Person>) savedInstanceState.getSerializable("actors");
            tvSeries = (List<Movie>) savedInstanceState.getSerializable("tvSeries");
            setInfo();
        } else {
            Bundle args = getArguments();
            if (args != null) {
                String query = args.getString("query");
                loadMovieDetails(query);
                textQuery.setText("Results: " + query);
            }
        }


    }

    private void loadMovieDetails(String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movies = TMDBApi.getSearchResultsMovie(query);
                tvSeries = TMDBApi.getSearchResultsTV(query);
                actors = TMDBApi.getSearchResultsPerson(query);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInfo();
                    }
                });
            }
        }).start();
    }


    private void setInfo() {
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
        searchAdapter = new SearchAdapter(tvSeries);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("movies", (Serializable) movies);
        outState.putSerializable("actors", (Serializable) actors);
        outState.putSerializable("tvSeries", (Serializable) tvSeries);
    }

}