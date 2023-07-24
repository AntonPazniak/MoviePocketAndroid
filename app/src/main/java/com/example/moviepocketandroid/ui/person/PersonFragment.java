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
import com.example.moviepocketandroid.adapter.TVSeriesAdapter;
import com.example.moviepocketandroid.api.ActorTMDBApi;
import com.example.moviepocketandroid.api.models.Actor;
import com.example.moviepocketandroid.api.models.Movie;
import com.example.moviepocketandroid.api.MovieTMDBApi;
import com.example.moviepocketandroid.api.models.tv.TVSeries;

import java.util.List;

public class PersonFragment extends Fragment {

    private PersonViewModel mViewModel;
    private int idPerson;

    private ImageView imagePerson;
    private TextView textNamePerson;
    private TextView textBiography;
    private TextView textPlaceBirth;
    private TextView textBirthday;
    private RecyclerView moviesRecyclerView;
    private RecyclerView tvRecyclerView;


    private MovieAdapter movieAdapter;
    private TVSeriesAdapter tvSeriesAdapter;

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
        textBiography = view.findViewById(R.id.textBiography);
        textPlaceBirth = view.findViewById(R.id.textPlaceBirth);
        textBirthday =view.findViewById(R.id.textBirthday);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        tvRecyclerView = view.findViewById(R.id.tvRecyclerView);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idPerson");
            loadPersonDetails(idMovie);
        }
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
                List<TVSeries> tvSeries = tmdbApi.getTVByIdActor(idPerson);
                if (actorTMDBS != null ) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            RequestOptions requestOptions = new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL);
                            System.out.println(actorTMDBS.getProfilePath());
                            Glide.with(requireContext())
                                    .load(actorTMDBS.getProfilePath())
                                    .apply(requestOptions)
                                    .into(imagePerson);
                            textNamePerson.setText(actorTMDBS.getName());
                            textBiography.setText(actorTMDBS.getBiography());
                            textPlaceBirth.setText(actorTMDBS.getPlaceOfBirth());
                            if(!actorTMDBS.getDeathDay().isEmpty())
                                textBirthday.setText(actorTMDBS.getBirthday());
                            else
                                textBirthday.setText(actorTMDBS.getBirthday()+" - "+actorTMDBS.getDeathDay());

                            if ( movies != null ) {
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
                            }
                            if (tvSeries != null) {
                                tvSeriesAdapter = new TVSeriesAdapter(tvSeries);
                                tvRecyclerView.setAdapter(tvSeriesAdapter);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                                tvRecyclerView.setLayoutManager(layoutManager2);
                                tvSeriesAdapter.setOnMovieClickListener(new TVSeriesAdapter.OnMovieClickListener() {
                                    @Override
                                    public void onMovieClick(int idTV) {
                                        Bundle args = new Bundle();
                                        args.putInt("idTV", idTV);
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_personFragment_to_TVSeriesFragment, args);
                                    }
                                });
                            }



                        }
                    });
                }
            }
        }).start();
    }

}