package com.example.moviepocketandroid.ui.home;

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
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.NowPlayingMovieAdapter;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;

    private List<Movie> movieInfoTMDBList;
    private FragmentHomeBinding binding;
    private int popularMovie;

    private TextView textTitlePopularMovie, textViewCinema;
    private ImageView imageBackMovie;
    private ImageView imagePosterMovie;


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

        Random random = new Random();
        popularMovie = random.nextInt(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                TMDBApi tmdbApi = new TMDBApi();
                movieInfoTMDBList = tmdbApi.getPopularMovies();
                List<Movie> nowPlayMovie = tmdbApi.getNowPlayingMovie();
                if (!movieInfoTMDBList.isEmpty()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RequestOptions requestOptions = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(requireContext())
                                    .load(movieInfoTMDBList.get(popularMovie).getBackdropPath())
                                    .apply(requestOptions)
                                    .into(imageBackMovie);
                            Glide.with(requireContext())
                                    .load(movieInfoTMDBList.get(popularMovie).getPosterPath())
                                    .apply(requestOptions)
                                    .into(imagePosterMovie);
                            textTitlePopularMovie.setText(movieInfoTMDBList.get(popularMovie).getTitle());

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

                        }
                    });
                }
            }
        }).start();

        imagePosterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                int idMovie = movieInfoTMDBList.get(popularMovie).getId();
                args.putInt("idMovie", idMovie);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_navigation_home_to_movieFragment, args);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}