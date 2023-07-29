package com.example.moviepocketandroid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.TMDB.MovieTMDBApi;
import com.example.moviepocketandroid.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {


    private List<Movie> movieInfoTMDBList;
    private FragmentHomeBinding binding;

    private int popularMovie;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        Random random = new Random();
        popularMovie = random.nextInt(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieTMDBApi tmdbApi = new MovieTMDBApi();
                movieInfoTMDBList = tmdbApi.getPopularMovies();
                if (!movieInfoTMDBList.isEmpty()) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RequestOptions requestOptions = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(requireContext())
                                    .load(movieInfoTMDBList.get(popularMovie).getBackdropPath())
                                    .apply(requestOptions)
                                    .into(binding.imageBackPopularMovie);
                            Glide.with(requireContext())
                                    .load(movieInfoTMDBList.get(popularMovie).getPosterPath())
                                    .apply(requestOptions)
                                    .into(binding.imagePosterPopularMovie);
                            binding.textTitlePopularMovie.setText(movieInfoTMDBList.get(popularMovie).getTitle());
                        }
                    });
                }
            }
        }).start();

        binding.imagePosterPopularMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                int idMovie = movieInfoTMDBList.get(popularMovie).getId();
                args.putInt("idMovie", idMovie);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_navigation_home_to_movieFragment, args);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}