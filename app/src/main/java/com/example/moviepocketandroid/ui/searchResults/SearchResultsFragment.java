package com.example.moviepocketandroid.ui.searchResults;

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
import android.widget.TextView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.SearchAdapter;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.MovieTMDBApi;

import java.util.List;

public class SearchResultsFragment extends Fragment {


    private SearchAdapter searchAdapter;
    private RecyclerView searchRecyclerView;
    private TextView textQuery;

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

        Bundle args = getArguments();
        if (args != null) {
            String query = args.getString("query");
            loadMovieDetails(query);
            textQuery.setText("Results: "+query);
        }


    }

    private void loadMovieDetails(String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieTMDBApi tmdbApi = new MovieTMDBApi();
                List<Movie> movies = tmdbApi.getSearchResults(query);
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
                                    navController.navigate(R.id.movieFragment, args);
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}