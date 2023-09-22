package com.example.moviepocketandroid.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.MovieTokAdapter;
import com.example.moviepocketandroid.api.TMDB.MovieTMDBApi;
import com.example.moviepocketandroid.api.models.Movie;

import java.util.List;

public class UserFragment extends Fragment {
    ViewPager2 viewPager;
    private MovieTokAdapter adapterMovieTok;
    private RecyclerView recyclerViewMovieTok;

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
        viewPager = view.findViewById(R.id.viewPager);
        loadMovieDetails(/* pass the appropriate idMovie here */);
    }

    private void loadMovieDetails() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieTMDBApi tmdbApi = new MovieTMDBApi();
                List<Movie> movies = tmdbApi.getPopularMovies();
                if (movies != null) {
                    adapterMovieTok = new MovieTokAdapter(requireContext(), movies);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MovieTokAdapter adapter = new MovieTokAdapter(requireContext(), movies);
                            viewPager.setAdapter(adapter);
                        }
                    });
                }
            }
        }).start();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}