package com.example.moviepocketandroid.ui.movie;

import static com.example.moviepocketandroid.animation.Animation.createAnimation;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorsAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.MovieTMDBApi;

import java.text.DecimalFormat;
import java.util.List;

public class MovieFragment extends Fragment {

    private MovieViewModel mViewModel;
    private ImageView imageBackPopularMovie;
    private ImageView imagePosterPopularMovie;
    private TextView textTitlePopularMovie;
    private ImageView imageStar;
    private ImageView imageEye;
    private ImageView imageLike;
    private ImageView imageBackPack;
    private TextView textOverview;
    private TextView textRating;
    private RecyclerView actorsRecyclerView;
    private ActorsAdapter actorsAdapter;
    private MovieAdapter movieAdapter;

    private boolean isLikeButtonPressed = false;
    private boolean isStarButtonPressed = false;
    private boolean isEyeButtonPressed = false;
    private boolean isBackPackButtonPressed = false;
    private RecyclerView moviesRecyclerView;

    AnimationSet animation;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageBackPopularMovie = view.findViewById(R.id.imageBackMovie);
        imagePosterPopularMovie = view.findViewById(R.id.imagePosterMovie);
        textTitlePopularMovie = view.findViewById(R.id.textTitlePopularMovie);
        imageStar = view.findViewById((R.id.imageStar));
        imageEye = view.findViewById(R.id.imageEye);
        imageLike = view.findViewById(R.id.imageLike);
        imageBackPack = view.findViewById(R.id.imageBackPack);
        textOverview = view.findViewById(R.id.textOverview);
        textRating = view.findViewById(R.id.textRating);
        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);

        this.animation = createAnimation();

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idMovie");
            loadMovieDetails(idMovie);
        }

        imageStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarButtonPressed)
                    imageStar.setImageResource(R.drawable.star_blue);
                else
                    imageStar.setImageResource(R.drawable.star_yellow);
                isStarButtonPressed = !isStarButtonPressed;
                imageStar.startAnimation(animation);;
            }
        });

        imageEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEyeButtonPressed)
                    imageEye.setImageResource(R.drawable.eye_blue);
                else
                    imageEye.setImageResource(R.drawable.eye_green);
                isEyeButtonPressed = !isEyeButtonPressed;
                imageEye.startAnimation(animation);
            }
        });

        imageBackPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBackPackButtonPressed)
                    imageBackPack.setImageResource(R.drawable.backpack_blue);
                else
                    imageBackPack.setImageResource(R.drawable.backpack_yellow);
                isBackPackButtonPressed = !isBackPackButtonPressed;
                imageBackPack.startAnimation(animation);
            }
        });

        imageLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLikeButtonPressed)
                    imageLike.setImageResource(R.drawable.like_blue);
                else
                    imageLike.setImageResource(R.drawable.like_pink);
                isLikeButtonPressed = !isLikeButtonPressed;
                imageLike.startAnimation(animation);
            }
        });


    }

    private void loadMovieDetails(int idMovie) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MovieTMDBApi tmdbApi = new MovieTMDBApi();
                Movie movieInfoTMDB = tmdbApi.getMovieDetails(idMovie);
                List<Actor> actors = tmdbApi.getActorsByIdMovie(idMovie);
                List<Movie> movies = tmdbApi.getSimilarMoviesById(idMovie);
                if (movieInfoTMDB != null) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RequestOptions requestOptions = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(requireContext())
                                    .load(movieInfoTMDB.getBackdropPath())
                                    .apply(requestOptions)
                                    .into(imageBackPopularMovie);
                            Glide.with(requireContext())
                                    .load(movieInfoTMDB.getPosterPath())
                                    .apply(requestOptions)
                                    .into(imagePosterPopularMovie);
                            textTitlePopularMovie.setText(movieInfoTMDB.getTitle());
                            textOverview.setText(movieInfoTMDB.getOverview());
                            DecimalFormat decimalFormat = new DecimalFormat("#.#");
                            textRating.setText(decimalFormat.format(movieInfoTMDB.getVoteAverage()));

                            actorsAdapter = new ActorsAdapter(actors);
                            actorsRecyclerView.setAdapter(actorsAdapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                            actorsRecyclerView.setLayoutManager(layoutManager);

                            actorsAdapter.setOnActorClickListener(new ActorsAdapter.OnActorClickListener() {
                                @Override
                                public void onActorClick(int actorId) {
                                    // Navigate to PersonFragment with actorId as an argument
                                    Bundle args = new Bundle();
                                    args.putInt("idPerson", actorId);

                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigate(R.id.action_movieFragment_to_personFragment, args);
                                }
                            });

                            if ( movies != null ) {
                                movieAdapter = new MovieAdapter(movies);
                                moviesRecyclerView.setAdapter(movieAdapter);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                                moviesRecyclerView.setLayoutManager(layoutManager1);
                                movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
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
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
    }


}
