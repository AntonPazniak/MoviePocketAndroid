package com.example.moviepocketandroid.ui.tvSeries;

import static com.example.moviepocketandroid.animation.Animation.createAnimation;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorsAdapter;
import com.example.moviepocketandroid.adapter.ImagesAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.TVSeriesAdapter;
import com.example.moviepocketandroid.api.TMDB.TVSeriesTMDBApi;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.MovieImage;
import com.example.moviepocketandroid.api.models.tv.TVSeries;

import java.text.DecimalFormat;
import java.util.List;

public class TVSeriesFragment extends Fragment {

    private TVSeriesViewModel mViewModel;
    private ImageView imageBackPopularMovie;
    private ImageView imagePosterPopularMovie;
    private TextView textTitlePopularMovie;
    private ImageView imageStar;
    private ImageView imageEye;
    private ImageView imageLike;
    private ImageView imageBackPack;
    private TextView textOverview;
    private TextView textRating;
    private TextView textVoteCount;
    private TextView textActorsRecyclerView;
    private TextView textTVRecyclerView;
    private TextView textCountry;
    private TextView textCategories;
    private TextView textMinutes;
    private RecyclerView actorsRecyclerView;
    private RecyclerView imagesRecyclerView;
    private ActorsAdapter actorsAdapter;
    private MovieAdapter movieAdapter;

    private boolean isLikeButtonPressed = false;
    private boolean isStarButtonPressed = false;
    private boolean isEyeButtonPressed = false;
    private boolean isBackPackButtonPressed = false;
    private boolean isExpanded = false;
    private RecyclerView tvRecyclerView;
    private TVSeriesAdapter tvSeriesAdapter;
    private ImagesAdapter movieImagesAdapter;



    private AnimationSet animation;

    public static TVSeriesFragment newInstance() {
        return new TVSeriesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_series, container, false);
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
        tvRecyclerView = view.findViewById(R.id.tvRecyclerView);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        textVoteCount = view.findViewById(R.id.textVoteCount);
        textActorsRecyclerView = view.findViewById(R.id.textActorsRecyclerView);
        textTVRecyclerView = view.findViewById(R.id.textTVRecyclerView);
        textCountry = view.findViewById(R.id.textCountry);
        textCategories = view.findViewById(R.id.textCategories);
        textMinutes = view.findViewById(R.id.textMinutes);

        this.animation = createAnimation();

        Bundle args = getArguments();
        if (args != null) {
            int idTV = args.getInt("idTV");
            loadMovieDetails(idTV);
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
                if (isLikeButtonPressed)
                    imageLike.setImageResource(R.drawable.like_blue);
                else
                    imageLike.setImageResource(R.drawable.like_pink);
                isLikeButtonPressed = !isLikeButtonPressed;
                imageLike.startAnimation(animation);
            }
        });

        textOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    textOverview.setMaxLines(5);
                    textOverview.setEllipsize(TextUtils.TruncateAt.END);
                } else {
                    textOverview.setMaxLines(Integer.MAX_VALUE);
                    textOverview.setEllipsize(null);
                }
                isExpanded = !isExpanded;
            }
        });

    }
    private void loadMovieDetails(int idTV) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TVSeriesTMDBApi tmdbApi = new TVSeriesTMDBApi();
                TVSeries tv = tmdbApi.getTVsInfo(idTV);
                List<Actor> actors = tmdbApi.getActorsByIdTV(idTV);
                List<TVSeries> tvSeries = tmdbApi.getSimilarTVById(idTV);
                List<MovieImage> images = tmdbApi.getImagesByIdTV(idTV);
                if (tv != null) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            RequestOptions requestOptions = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(requireContext())
                                    .load(tv.getPosterPath())
                                    .apply(requestOptions)
                                    .into(imagePosterPopularMovie);
                            Glide.with(requireContext())
                                    .load(tv.getBackdropPath())
                                    .apply(requestOptions)
                                    .into(imageBackPopularMovie);
                            textTitlePopularMovie.setText(tv.getName());
                            StringBuilder s = new StringBuilder();
                            if (!tv.getProductionCountries().isEmpty()) {
                                s.append(tv.getProductionCountries().get(0));
                                for (int i = 1; i < tv.getProductionCountries().size(); i++) {
                                    s.append(", ");
                                    s.append(tv.getProductionCountries().get(i));
                                }
                                textCountry.setText(s);
                            }
                            StringBuilder genders = new StringBuilder();
                            textMinutes.setText(tv.getFirstAirDate().substring(0, 4) + ", " + tv.getSeasons().size() + " Seasons, " + tv.getNumberOfEpisodes() + " Episode");
                            if (!tv.getGenres().isEmpty()) {
                                genders.append(tv.getGenres().get(0));
                                for (int i = 1; i < tv.getGenres().size(); i++) {
                                    genders.append(", ");
                                    genders.append(tv.getGenres().get(i));
                                }
                                textCategories.setText(genders);
                            }
                            if (tv.getVoteAverage() != 0) {
                                double rating = tv.getVoteAverage();
                                textOverview.setText(tv.getOverview());
                                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                                if (rating >= 8)
                                    textRating.setTextColor(Color.parseColor("#F1B36E"));
                                else if (rating >= 5)
                                    textRating.setTextColor(Color.parseColor("#75FBE2"));
                                else
                                    textRating.setTextColor(Color.parseColor("#E4416A"));
                                textRating.setText(decimalFormat.format(rating));
                                textVoteCount.setText("Votes: " + tv.getVoteCount());
                            }
                            textOverview.setText(tv.getOverview());
                            if (images != null) {
                                movieImagesAdapter = new ImagesAdapter(images);
                                imagesRecyclerView.setAdapter(movieImagesAdapter);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                                imagesRecyclerView.setLayoutManager(layoutManager2);
                            }
                            if (actors != null) {
                                textActorsRecyclerView.setText("Actors:");
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
                                        navController.navigate(R.id.action_TVSeriesFragment_to_personFragment, args);
                                    }
                                });
                                if (tvSeries != null) {
                                    textTVRecyclerView.setText("Similar TV Series:");
                                    tvSeriesAdapter = new TVSeriesAdapter(tvSeries);
                                    tvRecyclerView.setAdapter(tvSeriesAdapter);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                                    tvRecyclerView.setLayoutManager(layoutManager1);
                                    tvSeriesAdapter.setOnMovieClickListener(new TVSeriesAdapter.OnMovieClickListener() {
                                        @Override
                                        public void onMovieClick(int idTV) {
                                            Bundle args = new Bundle();
                                            args.putInt("idTV", idTV);
                                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                            navController.navigate(R.id.action_TVSeriesFragment_self, args);
                                        }
                                    });
                                }
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
        mViewModel = new ViewModelProvider(this).get(TVSeriesViewModel.class);
        // TODO: Use the ViewModel
    }

}