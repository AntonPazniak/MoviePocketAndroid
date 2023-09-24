package com.example.moviepocketandroid.ui.person;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.ImagesAdapter;
import com.example.moviepocketandroid.api.TMDB.ActorTMDBApi;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.models.MovieImage;

import java.util.List;

public class PersonFragment extends Fragment {

    private PersonViewModel mViewModel;
    private int idPerson;
    private ImageView imagePerson;
    private TextView textNamePerson, textOverview, textPlaceBirth, textBirthday, textMoviesRecyclerView, textTVRecyclerView;
    private RecyclerView moviesRecyclerView, tvRecyclerView, imagesRecyclerView;
    private ImagesAdapter movieImagesAdapter;
    private MovieAdapter movieAdapter;
    private MovieAdapter tvAdapter;
    private View viewImages, viewMovie, viewTvs;
    private boolean isExpanded = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imagePerson = view.findViewById(R.id.imagePerson);
        textNamePerson = view.findViewById(R.id.textNamePerson);
        textOverview = view.findViewById(R.id.textOverview);
        textPlaceBirth = view.findViewById(R.id.textPlaceBirth);
        textBirthday = view.findViewById(R.id.textBirthday);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        tvRecyclerView = view.findViewById(R.id.tvRecyclerView);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        textMoviesRecyclerView = view.findViewById(R.id.textMoviesRecyclerView);
        textTVRecyclerView = view.findViewById(R.id.textTVRecyclerView);
        viewImages = view.findViewById(R.id.viewImages);
        viewMovie = view.findViewById(R.id.viewSimilar);
        viewTvs = view.findViewById(R.id.viewTVs);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idPerson");
            loadPersonDetails(idMovie);
        }

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadPersonDetails(int idPerson){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActorTMDBApi tmdbApi = new ActorTMDBApi();
                Actor actorTMDBS = tmdbApi.getPersonById(idPerson);
                List<Movie> movies = tmdbApi.getMoviesByIdActor(idPerson);
                List<Movie> tvSeries = tmdbApi.getTVByIdActor(idPerson);
                List<MovieImage> images = tmdbApi.getImagesByIdActor(idPerson);
                if (actorTMDBS != null) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setImagePerson(actorTMDBS);
                            setInfoPerson(actorTMDBS);
                            setImages(images);
                            setMovies(movies);
                            setTVs(tvSeries);
                        }
                    });
                }
            }
        }).start();
    }

    private void setImagePerson(Actor actor) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(requireContext())
                .load(actor.getProfilePath())
                .apply(requestOptions)
                .into(imagePerson);
    }

    @SuppressLint("SetTextI18n")
    private void setInfoPerson(Actor actor) {
        if (!actor.getName().equals("null"))
            textNamePerson.setText(actor.getName());
        if (!actor.getPlaceOfBirth().equals("null"))
            textPlaceBirth.setText(actor.getPlaceOfBirth());
        if (!actor.getBiography().equals("null"))
            textOverview.setText(actor.getBiography());
        if (!actor.getDeathDay().equals("null") && !actor.getBiography().equals("null"))
            textBirthday.setText(actor.getBirthday() + " - " + actor.getDeathDay());
        else if (!actor.getBirthday().equals("null"))
            textBirthday.setText(actor.getBirthday());

    }

    private void setImages(List<MovieImage> images) {
        if (images.size() > 0) {
            movieImagesAdapter = new ImagesAdapter(images);
            imagesRecyclerView.setAdapter(movieImagesAdapter);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            imagesRecyclerView.setLayoutManager(layoutManager2);
        } else
            viewImages.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setMovies(List<Movie> movies) {
        if (movies.size() > 0) {
            textMoviesRecyclerView.setText("Movies:");
            movieAdapter = new MovieAdapter(movies);
            moviesRecyclerView.setAdapter(movieAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            moviesRecyclerView.setLayoutManager(layoutManager);
            movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
                @Override
                public void onMovieClick(int movieId) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movieId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_personFragment_to_movieFragment, args);
                }
            });
        } else
            viewMovie.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setTVs(List<Movie> tv) {
        if (tv.size() > 0) {
            textTVRecyclerView.setText("TV Series:");
            tvAdapter = new MovieAdapter(tv);
            tvRecyclerView.setAdapter(tvAdapter);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            tvRecyclerView.setLayoutManager(layoutManager1);
            tvAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
                @Override
                public void onMovieClick(int movieId) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movieId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_personFragment_to_movieFragment, args);
                }
            });
        } else
            viewTvs.setVisibility(View.GONE);
    }

}