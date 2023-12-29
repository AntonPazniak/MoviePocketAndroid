package com.example.moviepocketandroid.ui.movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
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

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorsAdapter;
import com.example.moviepocketandroid.adapter.ImagesAdapter;
import com.example.moviepocketandroid.adapter.ListAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.PostAdapter;
import com.example.moviepocketandroid.adapter.ReviewAdapter;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.MP.MPRatingApi;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.api.models.movie.ImageMovie;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.person.Person;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.api.models.review.Review;
import com.example.moviepocketandroid.ui.dialog.RatingDialog;
import com.example.moviepocketandroid.ui.until.ButtonUntil;
import com.example.moviepocketandroid.ui.until.MovieInfoUntil;
import com.example.moviepocketandroid.ui.until.RatingUntil;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MovieFragment extends Fragment {

    private MovieViewModel mViewModel;
    private TextView textImages, textViewOverview;
    private TextView textActorsRecyclerView, textMoviesRecyclerView;
    private ActorsAdapter actorsAdapter;
    private MovieAdapter movieAdapter;
    private ImagesAdapter movieImagesAdapter;
    private ReviewAdapter reviewAdapter;
    private View viewYouTube, viewImages, viewActors, viewSimilar, viewOverview;
    private RecyclerView actorsRecyclerView, moviesRecyclerView, imagesRecyclerView, reviewRecyclerView;
    private WebView webView;
    private Button button2, button;
    private View view;
    private View layoutList;
    private Context context;
    private Movie movie;
    private List<Person> actors;
    private List<Movie> similarMovies;
    private List<ImageMovie> images;
    private String movieTrailerUrl;
    private Boolean isAuthentication;
    private List<Review> reviews;
    private int idMovie, rating;
    private List<MovieList> lists;
    private ListAdapter listAdapter;
    private RecyclerView listRecyclerView;
    private TextView textViewList;
    private List<Post> posts;


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

        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        reviewRecyclerView = view.findViewById(R.id.recyclerViewReview);

        textActorsRecyclerView = view.findViewById(R.id.textActorsRecyclerView);
        textMoviesRecyclerView = view.findViewById(R.id.textMoviesRecyclerView);
        textImages = view.findViewById(R.id.textImages);
        textViewOverview = view.findViewById(R.id.textViewOverview);

        viewYouTube = view.findViewById(R.id.viewYouTube);
        viewImages = view.findViewById(R.id.viewImages);
        viewActors = view.findViewById(R.id.viewActors);
        viewSimilar = view.findViewById(R.id.viewSimilar);
        viewOverview = view.findViewById(R.id.viewOverview);

        button2 = view.findViewById(R.id.button2);
        button = view.findViewById(R.id.button);

        webView = view.findViewById(R.id.webView);
        webView.setBackgroundColor(0);

        layoutList = view.findViewById(R.id.layoutList);

        context = view.getContext();
        this.view = view;

        Bundle args = getArguments();
        if (args != null) {
            idMovie = args.getInt("idMovie");
        }

        if (savedInstanceState != null) {
            movie = (Movie) savedInstanceState.getSerializable("movie");
            images = (List<ImageMovie>) savedInstanceState.getSerializable("images");
            similarMovies = (List<Movie>) savedInstanceState.getSerializable("similarMovies");
            actors = (List<Person>) savedInstanceState.getSerializable("actors");
            reviews = (List<Review>) savedInstanceState.getSerializable("reviews");
            posts = (List<Post>) savedInstanceState.getSerializable("posts");
            movieTrailerUrl = savedInstanceState.getString("movieTrailerUrl");
            rating = savedInstanceState.getInt("rating");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isAuthentication = MPAuthenticationApi.checkAuth();
                    if (movie != null && isAdded()) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setInfo();
                            }
                        });
                    } else
                        loadMovieDetails(idMovie);
                }
            }).start();

        } else
            loadMovieDetails(idMovie);


    }
    private void loadMovieDetails(int idMovie) {

        if (movie == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isAuthentication = MPAuthenticationApi.checkAuth();
                    movie = TMDBApi.getInfoMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MovieInfoUntil movieInfoUntil = new MovieInfoUntil(view, movie);
                            movieInfoUntil.setMovieInfo();
                        }
                    });
                    rating = MPRatingApi.getRatingUserByIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RatingDialog ratingDialog1 = new RatingDialog(view, idMovie, rating);
                            ratingDialog1.setRatingDialog(isAuthentication, getRelease());
                            ButtonUntil buttonUntil = new ButtonUntil(view, idMovie, getRelease(), isAuthentication);
                            RatingUntil ratingUntil = new RatingUntil(view, idMovie);
                            ratingUntil.setRating();
                        }
                    });
                    actors = TMDBApi.getActorsByIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMovieActors(actors);
                        }
                    });
                    similarMovies = TMDBApi.getSimilarMoviesById(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMovieSimilar(similarMovies);
                        }
                    });
                    images = TMDBApi.getImagesByIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMovieImages(images);
                        }
                    });
                    movieTrailerUrl = TMDBApi.getMovieTrailerUrl(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMovieTrailer(movieTrailerUrl);
                        }
                    });
                    reviews = MPReviewApi.getReviewAllByIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMovieReview(reviews);
                            setButtonsReview();
                        }
                    });
                    lists = MPListApi.getAllListExistIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setLists();
                        }
                    });
                    posts = MPPostApi.getAllPostExistIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setPostAdapter();
                        }
                    });
                }
            }).start();
        } else
            setInfo();
    }

    private void setInfo() {
        MovieInfoUntil movieInfoUntil = new MovieInfoUntil(view, movie);
        movieInfoUntil.setMovieInfo();
        RatingUntil ratingUntil = new RatingUntil(view, idMovie);
        ratingUntil.setRating();
        RatingDialog ratingDialog1 = new RatingDialog(view, idMovie, rating);
        ratingDialog1.setRatingDialog(isAuthentication, getRelease());
        ButtonUntil buttonUntil = new ButtonUntil(view, idMovie, getRelease(), isAuthentication);
        setButtonsReview();
        setMovieTrailer(movieTrailerUrl);
        setMovieImages(images);
        setMovieActors(actors);
        setMovieSimilar(similarMovies);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (movie != null && isAdded()) {
                    lists = MPListApi.getAllListExistIdMovie(idMovie);
                    reviews = MPReviewApi.getReviewAllByIdMovie(idMovie);
                    posts = MPPostApi.getAllPostExistIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMovieReview(reviews);
                            setLists();
                            setPostAdapter();
                        }
                    });
                }
            }
        }).start();
    }

    private void setButtonsReview() {
        if (isAuthentication) {
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movie.getId());

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_newReviewFragment, args);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("idMovie", movie.getId());

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_allReviewFragment, args);
                }
            });
        } else {

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_loginFragment);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_loginFragment);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        // TODO: Use the ViewModel
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setMovieTrailer(String movieTrailerUrl) {
        if (movieTrailerUrl != null) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            String frameVideo = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"" + movieTrailerUrl +
                    "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            webView.loadData(frameVideo, "text/html", "utf-8");
        } else
            viewYouTube.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setMovieImages(List<ImageMovie> images) {
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
    private void setMovieActors(List<Person> actors) {
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
                    navController.navigate(R.id.action_movieFragment_to_personFragment, args);
                }
            });
        } else
            viewActors.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setMovieSimilar(List<Movie> movies) {
        if (movies != null) {
            textMoviesRecyclerView.setText("Similar:");
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
                    navController.navigate(R.id.action_movieFragment_self, args);
                }
            });
        } else
            viewSimilar.setVisibility(View.GONE);
    }

    private void setMovieReview(List<Review> reviews) {
        if (reviews != null) {
            TextView textReview = view.findViewById(R.id.textReview);
            textReview.setText(R.string.reviews);

            reviewAdapter = new ReviewAdapter(reviews);
            reviewRecyclerView.setAdapter(reviewAdapter);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            reviewRecyclerView.setLayoutManager(layoutManager2);
            reviewAdapter.setOnReviewClickListener(new ReviewAdapter.OnReviewClickListener() {
                @Override
                public void onReviewClick(int reviewId) {
                    Bundle args = new Bundle();
                    args.putInt("idReview", reviewId);

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_detailReviewFragment, args);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void setLists() {
        listRecyclerView = layoutList.findViewById(R.id.moviesRecyclerView);
        textViewList = layoutList.findViewById(R.id.textMoviesRecyclerView);

        if (lists != null && !lists.isEmpty()) {
            textViewList.setText("Lists with this movie");
            listAdapter = new ListAdapter(lists);
            listRecyclerView.setAdapter(listAdapter);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            listRecyclerView.setLayoutManager(layoutManager1);
            listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int idList) {
                    Bundle args = new Bundle();
                    args.putInt("idList", idList);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_listFragment, args);
                }
            });
            listRecyclerView.setVisibility(View.VISIBLE);
            textViewList.setVisibility(View.VISIBLE);
        } else {
            listRecyclerView.setVisibility(View.GONE);
            textViewList.setVisibility(View.GONE);
            layoutList.setVisibility(View.GONE);
        }
    }

    private void setPostAdapter() {
        if (posts != null && !posts.isEmpty()) {
            TextView textView = view.findViewById(R.id.textPost);
            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPost);

            textView.setText(R.string.post_mov);
            PostAdapter postAdapter = new PostAdapter(posts);
            recyclerView.setAdapter(postAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int idPost) {
                    Bundle args = new Bundle();
                    args.putInt("idPost", idPost);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                    navController.navigate(R.id.action_movieFragment_to_postFragment, args);
                }
            });

        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movie != null) {
            outState.putSerializable("movie", (Serializable) movie);
            outState.putSerializable("images", (Serializable) images);
            outState.putSerializable("similarMovies", (Serializable) similarMovies);
            outState.putSerializable("actors", (Serializable) actors);
            outState.putSerializable("reviews", (Serializable) reviews);
            outState.putSerializable("posts", (Serializable) posts);
            outState.putString("movieTrailerUrl", movieTrailerUrl);
            outState.putInt("rating", rating);
        }
    }

    boolean getRelease() {
        if (idMovie > 0)
            return movie.getReleaseDate() != null && !movie.getReleaseDate().isAfter(LocalDate.now());
        else
            return movie.getFirstAirDate() != null && !movie.getFirstAirDate().isAfter(LocalDate.now());
    }

}
