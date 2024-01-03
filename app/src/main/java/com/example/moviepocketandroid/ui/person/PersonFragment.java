package com.example.moviepocketandroid.ui.person;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ImagesAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.PostAdapter;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.movie.ImageMovie;
import com.example.moviepocketandroid.api.models.person.Person;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.post.Post;

import java.io.Serializable;
import java.util.List;

public class PersonFragment extends Fragment {

    private PersonViewModel mViewModel;
    private int idPerson;
    private ImageView imagePerson;
    private TextView textNamePerson, textOverview, textPlaceBirth, textBirthday, textImages, textMoviesRecyclerView, textTVRecyclerView, textViewOverview, textDeathDay;
    private RecyclerView moviesRecyclerView, tvRecyclerView, imagesRecyclerView;
    private ImagesAdapter movieImagesAdapter;
    private MovieAdapter movieAdapter;
    private MovieAdapter tvAdapter;
    private View viewImages, viewMovie, viewTvs, viewOverview;
    private boolean isExpanded = false;
    private Person person;
    private List<Movie> movies;
    private List<Movie> tvSeries;
    private List<ImageMovie> images;
    private List<Post> posts;
    private ImageButton buttonNewPost, buttonAllPosts;
    private boolean isAuthentication;
    private RecyclerView recyclerViewPost;
    private TextView textPost;

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
        textDeathDay = view.findViewById(R.id.textDeathDay);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        tvRecyclerView = view.findViewById(R.id.tvRecyclerView);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        textMoviesRecyclerView = view.findViewById(R.id.textMoviesRecyclerView);
        textTVRecyclerView = view.findViewById(R.id.textTVRecyclerView);
        textImages = view.findViewById(R.id.textImages);

        viewImages = view.findViewById(R.id.viewImages);
        viewMovie = view.findViewById(R.id.viewSimilar);
        viewTvs = view.findViewById(R.id.viewTVs);
        textViewOverview = view.findViewById(R.id.textViewOverview);
        viewOverview = view.findViewById(R.id.viewOverview);

        textPost = view.findViewById(R.id.textPost);
        buttonNewPost = view.findViewById(R.id.buttonNewPost);
        buttonAllPosts = view.findViewById(R.id.buttonAllPosts);
        recyclerViewPost = view.findViewById(R.id.recyclerViewPost);

        Bundle args = getArguments();
        if (args != null) {
            idPerson = args.getInt("idPerson");
            if (savedInstanceState != null) {
                movies = (List<Movie>) savedInstanceState.getSerializable("movies");
                person = (Person) savedInstanceState.getSerializable("person");
                tvSeries = (List<Movie>) savedInstanceState.getSerializable("tvSeries");
                images = (List<ImageMovie>) savedInstanceState.getSerializable("images");
                if (movies != null)
                    setInfo();
            } else {
                loadPersonDetails(idPerson);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadPersonDetails(int idPerson) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (person == null) {
                    person = TMDBApi.getPersonById(idPerson);
                    movies = TMDBApi.getMoviesByIdActor(idPerson);
                    tvSeries = TMDBApi.getTVByIdActor(idPerson);
                    images = TMDBApi.getImagesByIdActor(idPerson);
                }

                if (person != null && isAdded()) {
                    isAuthentication = MPAuthenticationApi.checkAuth();
                    posts = MPPostApi.getAllPostExistIdPerson(idPerson);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setInfo();
                        }
                    });
                }
            }
        }).start();
    }


    private void setInfo() {


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
        setPosts();
        setImagePerson(person);
        setInfoPerson(person);
        setImages(images);
        setMovies(movies);
        setTVs(tvSeries);
    }

    private void setImagePerson(Person actor) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(requireContext())
                .load(actor.getProfilePath())
                .apply(requestOptions)
                .into(imagePerson);
    }

    @SuppressLint("SetTextI18n")
    private void setInfoPerson(Person actor) {
        if (actor.getName() != null)
            textNamePerson.setText(actor.getName());
        if (actor.getPlaceOfBirth() != null)
            textPlaceBirth.setText(actor.getPlaceOfBirth());
        if (actor.getBiography() != null) {
            textViewOverview.setText("Biography");
            textOverview.setText(actor.getBiography());
        } else
            viewOverview.setVisibility(View.GONE);
        if (actor.getDeathday() != null && actor.getBiography() != null) {
            textBirthday.setText("Birthday: " + actor.getBirthday());
            textDeathDay.setText("DeathDay: " + actor.getDeathday());
        } else if (actor.getBirthday() != null)
            textBirthday.setText("Birthday: " + actor.getBirthday());

    }

    @SuppressLint("SetTextI18n")
    private void setImages(List<ImageMovie> images) {
        if (images != null) {
            textImages.setText("Images:");
            movieImagesAdapter = new ImagesAdapter(images);
            imagesRecyclerView.setAdapter(movieImagesAdapter);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            imagesRecyclerView.setLayoutManager(layoutManager2);
        } else
            viewImages.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setMovies(List<Movie> movies) {
        if (movies != null) {
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
        if (tv != null) {
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

    private void setPosts() {
        textPost.setVisibility(View.VISIBLE);
        buttonNewPost.setVisibility(View.VISIBLE);
        buttonAllPosts.setVisibility(View.VISIBLE);
        if (isAuthentication) {
            buttonNewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("idPersonNewPost", idPerson);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_personFragment_to_newReviewFragment, args);
                }
            });
        } else {

            buttonNewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_personFragment_to_loginFragment);
                }
            });
        }
        buttonAllPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("idPerson", idPerson);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_personFragment_to_postAllFragment, args);
            }
        });

        if (posts != null && !posts.isEmpty()) {
            PostAdapter postAdapter = new PostAdapter(posts);
            recyclerViewPost.setAdapter(postAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false);
            recyclerViewPost.setLayoutManager(layoutManager);

            postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int idPost) {
                    Bundle args = new Bundle();
                    args.putInt("idPost", idPost);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_personFragment_to_postFragment, args);
                }
            });
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("movies", (Serializable) movies);
        outState.putSerializable("person", person);
        outState.putSerializable("tvSeries", (Serializable) tvSeries);
        outState.putSerializable("images", (Serializable) images);
    }

}