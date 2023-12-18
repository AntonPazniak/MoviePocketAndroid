package com.example.moviepocketandroid.ui.list.movie;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.models.MovieList;

public class ListMovieFragment extends Fragment {

    int idList;
    private ListMovieViewModel mViewModel;
    private TextView textViewTitle;
    private RecyclerView recyclerViewList;
    private MovieList movieList;

    public ListMovieFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }

    public ListMovieFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_movie, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            idList = args.getInt("idList");

            textViewTitle = view.findViewById(R.id.textViewTitle);
            recyclerViewList = view.findViewById(R.id.recyclerViewList);

            loadListInf();
        }

    }


    private void loadListInf() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieList = MPListApi.getListById(idList);
                if (movieList != null) {
                    if (isAdded()) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setListInf();
                            }
                        });
                    }
                }
            }
        }).start();

    }


    private void setListInf() {
        textViewTitle.setText(movieList.getTitle());

        if (movieList.getMovies() != null && !movieList.getMovies().isEmpty()) {
            MovieAdapter movieAdapter = new MovieAdapter(movieList.getMovies());
            recyclerViewList.setAdapter(movieAdapter);

            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false);
            recyclerViewList.setLayoutManager(layoutManager);

            movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
                @Override
                public void onMovieClick(int movieId) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movieId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_listFragment_to_movieFragment, args);
                }
            });
        }

    }

}