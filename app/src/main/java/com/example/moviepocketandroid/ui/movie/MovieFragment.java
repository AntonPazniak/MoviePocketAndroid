package com.example.moviepocketandroid.ui.movie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ActorsAdapter;
import com.example.moviepocketandroid.adapter.ImagesAdapter;
import com.example.moviepocketandroid.adapter.ListAdapter;
import com.example.moviepocketandroid.adapter.MovieAdapter;
import com.example.moviepocketandroid.adapter.ReviewAdapter;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.MP.MPRatingApi;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.TMDB.TMDBApi;
import com.example.moviepocketandroid.api.models.movie.ImageMovie;
import com.example.moviepocketandroid.api.models.list.MovieList;
import com.example.moviepocketandroid.api.models.person.Person;
import com.example.moviepocketandroid.api.models.movie.Movie;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.api.models.review.Review;
import com.example.moviepocketandroid.ui.dialog.RatingDialog;
import com.example.moviepocketandroid.util.ButtonUntil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class MovieFragment extends Fragment {

    private MovieViewModel mViewModel;
    private ImageView imageBackPopularMovie, imagePosterPopularMovie;
    private TextView textTitlePopularMovie;
    private ImageView imageEye, imageLike, imageBackPack, imageBinoculars;
    private TextView textRating, textVoteCount, textOverview, textImages, textViewOverview;
    private TextView textActorsRecyclerView, textMoviesRecyclerView;
    private TextView textCountry, textCategories, textMinutes;
    private ActorsAdapter actorsAdapter;
    private MovieAdapter movieAdapter;
    private ImagesAdapter movieImagesAdapter;
    private ReviewAdapter reviewAdapter;
    private View viewYouTube, viewImages, viewActors, viewSimilar, viewOverview;
    private boolean isExpanded = false;
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

        imageBackPopularMovie = view.findViewById(R.id.imageBackMovie);
        imagePosterPopularMovie = view.findViewById(R.id.imagePosterMovie);
        textTitlePopularMovie = view.findViewById(R.id.textTitlePopularMovie);

        imageEye = view.findViewById(R.id.imageEye);
        imageLike = view.findViewById(R.id.imageLike);
        imageBackPack = view.findViewById(R.id.imageBackPack);
        imageBinoculars = view.findViewById(R.id.imageBinoculars);

        textOverview = view.findViewById(R.id.textOverview);
        textRating = view.findViewById(R.id.textRating);

        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView);
        moviesRecyclerView = view.findViewById(R.id.moviesRecyclerView);
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView);
        reviewRecyclerView = view.findViewById(R.id.recyclerViewReview);

        textVoteCount = view.findViewById(R.id.textVoteCount);
        textActorsRecyclerView = view.findViewById(R.id.textActorsRecyclerView);
        textMoviesRecyclerView = view.findViewById(R.id.textMoviesRecyclerView);
        textCountry = view.findViewById(R.id.textCountry);
        textCategories = view.findViewById(R.id.textCategories);
        textMinutes = view.findViewById(R.id.textMinutes);
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
            movieTrailerUrl = savedInstanceState.getString("movieTrailerUrl");
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                isAuthentication = MPAuthenticationApi.checkAuth();
                if (movie == null) {
                    movie = TMDBApi.getInfoMovie(idMovie);
                    actors = TMDBApi.getActorsByIdMovie(idMovie);
                    similarMovies = TMDBApi.getSimilarMoviesById(idMovie);
                    images = TMDBApi.getImagesByIdMovie(idMovie);
                    movieTrailerUrl = TMDBApi.getMovieTrailerUrl(idMovie);
                    rating = MPRatingApi.getRatingUserByIdMovie(idMovie);
                }

                if (movie != null && isAdded()) {
                    lists = MPListApi.getAllListExistIdMovie(idMovie);
                    reviews = MPReviewApi.getReviewAllByIdMovie(idMovie);
                    posts = MPPostApi.getAllPostExistIdMovie(idMovie);
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
        setButtonsReview();
        setPosterAndTitle(movie);
        setMovieInfo(movie);
        setMovieRating(movie);
        setMovieTrailer(movieTrailerUrl);
        setMovieImages(images);
        setMovieActors(actors);
        setMovieSimilar(similarMovies);
        setMovieReview(reviews);
        setLists();
    }

    private void setButtonsReview() {
        if (isAuthentication) {

            setButtons(movie);
            RatingDialog ratingDialog1 = new RatingDialog(view, idMovie, rating);
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

    private void setButtons(Movie movie) {
        if (movie.getReleaseDate() != null && movie.getReleaseDate().isAfter(LocalDate.now())) {
            movieNotReleased();
        } else {
            movieReleased();
        }
    }

    private void movieNotReleased() {
        ButtonUntil buttonUntil;
        imageBinoculars.setVisibility(View.VISIBLE);
        imageBackPack.setVisibility(View.VISIBLE);
        imageEye.setVisibility(View.GONE);
        imageLike.setVisibility(View.GONE);
        buttonUntil = new ButtonUntil(imageBackPack, imageBinoculars, movie.getId());
    }

    private void movieReleased() {
        ButtonUntil buttonUntil;
        imageEye.setVisibility(View.VISIBLE);
        imageLike.setVisibility(View.VISIBLE);
        imageBackPack.setVisibility(View.VISIBLE);
        imageBinoculars.setVisibility(View.GONE);
        buttonUntil = new ButtonUntil(imageEye, imageLike, imageBackPack, movie.getId());
    }


    private void setPosterAndTitle(Movie movie) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(requireContext())
                .load(movie.getBackdropPath())
                .apply(requestOptions)
                .into(imageBackPopularMovie);
        Glide.with(requireContext())
                .load(movie.getPosterPath())
                .apply(requestOptions)
                .into(imagePosterPopularMovie);
        textTitlePopularMovie.setText(movie.getTitle());
    }

    @SuppressLint("SetTextI18n")
    private void setMovieInfo(Movie movie) {
        StringBuilder s = new StringBuilder();
        if (movie.getProductionCountries() != null && !movie.getProductionCountries().isEmpty()) {
            s.append(movie.getProductionCountries().get(0).getName());
            for (int i = 1; i < movie.getProductionCountries().size(); i++) {
                s.append(", ");
                s.append(movie.getProductionCountries().get(i).getName());
            }
            textCountry.setText(s);
        }
        StringBuilder genders = new StringBuilder();
        if (movie.getReleaseDate() != null) {
            int year = movie.getReleaseDate().getYear();
            if (movie.getId() > 0) {
                textMinutes.setText(year + ", " + movie.getRuntime() + " mins");
            } else {
                textMinutes.setText(year + ", Seasons: " + movie.getSeasons().size());
            }
        }

        if (movie.getGenres() != null) {
            genders.append(movie.getGenres().get(0).getName());
            for (int i = 1; i < movie.getGenres().size(); i++) {
                genders.append(", ");
                genders.append(movie.getGenres().get(i).getName());
            }
            textCategories.setText(genders);
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

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void setMovieRating(Movie movie) {
        if (movie.getVoteAverage() != 0) {
            double rating = movie.getVoteAverage();
            if (!movie.getOverview().equals("null")) {
                textViewOverview.setText("Description");
                textOverview.setText(movie.getOverview());
            } else {
                viewOverview.setVisibility(View.GONE);
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            int color;
            if (rating >= 8) {
                color = ContextCompat.getColor(context, R.color.logoYellow);
            } else if (rating >= 5) {
                color = ContextCompat.getColor(context, R.color.logoBlue);
            } else {
                color = ContextCompat.getColor(context, R.color.logoPink);
            }

            textRating.setTextColor(color);
            textRating.setText(decimalFormat.format(rating));
            textVoteCount.setText("Votes: " + movie.getVoteCount());
        }
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


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movie != null) {
            outState.putSerializable("movie", (Serializable) movie);
            outState.putSerializable("images", (Serializable) images);
            outState.putSerializable("similarMovies", (Serializable) similarMovies);
            outState.putSerializable("actors", (Serializable) actors);
            outState.putSerializable("reviews", (Serializable) reviews);
            outState.putString("movieTrailerUrl", movieTrailerUrl);
        }
    }
}
