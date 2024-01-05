package com.example.moviepocketandroid.ui.list.add.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.search.SearchAdapter;
import com.example.moviepocketandroid.adapter.search.SearchAdapter2;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.util.List;

public class ListMovieAddSearchFragment extends Fragment {

    private ListMovieAddSearchViewModel mViewModel;

    private int idList;
    private RecyclerView recyclerView;
    private SearchAdapter2 searchAdapter;

    public ListMovieAddSearchFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }

    public ListMovieAddSearchFragment() {
    }


    public static ListMovieAddSearchFragment newInstance() {
        return new ListMovieAddSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_movie_add_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListMovieAddSearchViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        Bundle args = getArguments();
        if (args != null) {
            idList = args.getInt("idList");
            String movie = args.getString("movie", null);
            String tv = args.getString("tv", null);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (movie != null && !movie.isEmpty()) {
                        setSearchResultsMovie(TMDBApi.getSearchResultsMovie(movie));
                    } else if (tv != null && !tv.isEmpty()) {
                        setSearchResultsMovie(TMDBApi.getSearchResultsTV(tv));
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
                    searchAdapter = new SearchAdapter2(movies, idList);
                    recyclerView.setAdapter(searchAdapter);
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager2);
                    searchAdapter.setOnMovieClickListener(new SearchAdapter.OnMovieClickListener() {
                        @Override
                        public void onMovieClick(int movieId) {
                            Bundle args = new Bundle();
                            args.putInt("idMovie", movieId);

                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_listFragment_to_movieFragment, args);
                        }
                    });
                }
            });
        }
    }

    public void updateMovie(String movie) {
        Bundle args = new Bundle();
        args.putString("movie", movie);
        args.putInt("idList", idList);
        setArguments(args);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (movie != null && !movie.isEmpty())
                    setSearchResultsMovie(TMDBApi.getSearchResultsMovie(movie));
            }
        }).start();
    }

    public void updateTVs(String tv) {
        Bundle args = new Bundle();
        args.putString("tv", tv);
        args.putInt("idList", idList);
        setArguments(args);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (tv != null && !tv.isEmpty()) {
                    setSearchResultsMovie(TMDBApi.getSearchResultsTV(tv));
                }
            }
        }).start();
    }

}