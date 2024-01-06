package com.example.moviepocketandroid.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.NowPlayingMovieAdapter;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.databinding.FragmentHomeBinding;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;

    private List<Movie> moviePopular;
    private FragmentHomeBinding binding;
    private int id, idListMovie;
    private TextView textTitlePopularMovie, textViewCinema, textViewNameList;
    private ImageView imageBackMovie;
    private ImageView imagePosterMovie;
    private RecyclerView recyclerView;
    private ImageView imageViewBack;
    private List<Movie> nowPlayMovie;
    private MovieList movieList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageBackMovie = view.findViewById(R.id.imageBackMovie);
        imagePosterMovie = view.findViewById(R.id.imagePosterMovie);
        textTitlePopularMovie = view.findViewById(R.id.textTitlePopularMovie);

        textViewCinema = view.findViewById(R.id.textViewCinema);

        viewPager = view.findViewById(R.id.viewPager);

        recyclerView = view.findViewById(R.id.recyclerView);


        imageViewBack = view.findViewById(R.id.imageViewBack);
        textViewNameList = view.findViewById(R.id.textViewNameList);

        if (savedInstanceState != null) {
            moviePopular = Collections.checkedList(
                    (List<Movie>) savedInstanceState.getSerializable("moviePopular"), Movie.class);

            nowPlayMovie = Collections.checkedList(
                    (List<Movie>) savedInstanceState.getSerializable("nowPlayMovie"), Movie.class);

            movieList = (MovieList) savedInstanceState.getSerializable("movieList");

            setInfo();

        } else {

            new Thread(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    if (moviePopular == null) {
                        moviePopular = TMDBApi.getPopularMovies();
                    }

                    if (nowPlayMovie == null) {
                        nowPlayMovie = TMDBApi.getNowPlayingMovie();
                    }

                    if (movieList == null) {
                        movieList = MPListApi.getListById(3);
                    }

                    if (movieList != null) {
                        if (isAdded() && getContext() != null) {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setMainLIst();
                                }
                            });
                        }
                    }

                    if (moviePopular != null && !moviePopular.isEmpty()) {
                        if (isAdded() && getContext() != null) {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setInfo();
                                }
                            });
                        }
                    }
                }
            }).start();
        }
    }

    private void setInfo() {

        Random random = new Random();
        id = random.nextInt(moviePopular.size());


        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(requireContext())
                .load(moviePopular.get(id).getBackdropPath())
                .apply(requestOptions)
                .into(imageBackMovie);
        Glide.with(requireContext())
                .load(moviePopular.get(id).getPosterPath())
                .apply(requestOptions)
                .into(imagePosterMovie);
        textTitlePopularMovie.setText(moviePopular.get(id).getTitle());

        textViewCinema.setVisibility(View.VISIBLE);

        NowPlayingMovieAdapter adapter = new NowPlayingMovieAdapter(nowPlayMovie);
        viewPager.setAdapter(adapter);
        adapter.setOnMovieClickListener(new NowPlayingMovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(int movieId) {
                Bundle args = new Bundle();
                args.putInt("idMovie", movieId);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_navigation_home_to_movieFragment, args);
            }
        });

        imagePosterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                int idMovie = moviePopular.get(id).getId();
                args.putInt("idMovie", idMovie);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_navigation_home_to_movieFragment, args);
            }
        });


    }

    private void setMainLIst() {

        Random random = new Random();
        idListMovie = random.nextInt(movieList.getMovies().size());
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("idList", 3);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_navigation_home_to_listFragment, args);
            }

        });


        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(requireContext())
                .load(movieList.getMovies().get(idListMovie).getBackdropPath())
                .apply(requestOptions)
                .into(imageViewBack);
        textViewNameList.setVisibility(View.VISIBLE);
        textViewCinema.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("moviePopular", (Serializable) moviePopular);
        outState.putSerializable("nowPlayMovie", (Serializable) nowPlayMovie);
        outState.putSerializable("movieList", (Serializable) movieList);
    }


}