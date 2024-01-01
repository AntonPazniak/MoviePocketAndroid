package com.example.moviepocketandroid.ui.search.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.person.Person;

import java.util.List;

public class SearchRecyclerFragment extends Fragment {

    private SearchRecyclerViewModel mViewModel;
    private SearchAdapter searchAdapter;
    private ActorSearchAdapter actorSearchAdapter;
    private RecyclerView searchRecyclerView;

    public SearchRecyclerFragment() {
    }

    public SearchRecyclerFragment(String movie) {
        Bundle args = new Bundle();
        args.putString("movie", movie);
        setArguments(args);
    }

    public SearchRecyclerFragment(String tv, int n) {
        Bundle args = new Bundle();
        args.putString("tv", tv);
        setArguments(args);
    }

    public SearchRecyclerFragment(String person, int n, int n1) {
        Bundle args = new Bundle();
        args.putString("person", person);
        setArguments(args);
    }

    public static SearchRecyclerFragment newInstance() {
        return new SearchRecyclerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);

        Bundle args = getArguments();
        if (args != null) {
            String movie = args.getString("movie", null);
            String tv = args.getString("tv", null);
            String person = args.getString("person", null);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (movie != null && !movie.isEmpty()) {
                        setSearchResultsMovie(TMDBApi.getSearchResultsMovie(movie));
                    } else if (tv != null && !tv.isEmpty()) {
                        setSearchResultsMovie(TMDBApi.getSearchResultsTV(tv));
                    } else if (person != null && !person.isEmpty()) {
                        setSearchResultsPerson(TMDBApi.getSearchResultsPerson(person));
                    }
                }
            }).start();
        }

    }


    private void setSearchResultsMovie(List<Movie> movies) {

        if (movies != null && isAdded()) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
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
            });
        }
    }

    private void setSearchResultsPerson(List<Person> persons) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                actorSearchAdapter = new ActorSearchAdapter(persons);
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
        });

    }

}