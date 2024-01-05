package com.example.moviepocketandroid.adapter.search;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.MP.MPRatingApi;
import com.example.moviepocketandroid.api.models.movie.Genre;
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.text.DecimalFormat;
import java.util.List;

public class SearchAdapter2 extends RecyclerView.Adapter<SearchAdapter2.MovieViewHolder> {

    private List<Movie> movies;
    private int idList;
    private SearchAdapter.OnMovieClickListener onMovieClickListener;


    public SearchAdapter2(List<Movie> movies, int idList) {
        this.movies = movies;
        this.idList = idList;
    }

    public void setOnMovieClickListener(SearchAdapter.OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    @NonNull
    @Override
    public SearchAdapter2.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search_movie, parent, false);
        return new SearchAdapter2.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter2.MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnMovieClickListener {
        void onMovieClick(int movieId);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageMovie;
        private TextView textTitleMovie;
        private TextView textGenres;
        private TextView textYear;
        private TextView textRatingPoster;
        private ImageButton buttonAdd;
        private boolean exist;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.imagePosterMovieSearch);
            textTitleMovie = itemView.findViewById(R.id.textTitleMovieSearch);
            textYear = itemView.findViewById(R.id.textYear);
            textGenres = itemView.findViewById(R.id.textGenres);
            textRatingPoster = itemView.findViewById(R.id.textRatingPoster);
            buttonAdd = itemView.findViewById(R.id.buttonAdd);
        }

        public void bind(Movie movie) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .apply(requestOptions)
                    .into(imageMovie);
            textTitleMovie.setText(movie.getTitle());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    double rating = MPRatingApi.getRatingMovie(movie.getId());
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (rating >= 8) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(itemView.getContext(), R.color.logoYellow));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else if (rating > 4) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(itemView.getContext(), R.color.logoBlue));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else if (rating > 0) {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(itemView.getContext(), R.color.logoPink));
                                textRatingPoster.setText(decimalFormat.format(rating));
                            } else {
                                textRatingPoster.setBackgroundColor(ContextCompat
                                        .getColor(itemView.getContext(), R.color.grey));
                                textRatingPoster.setText(R.string.nr);
                            }
                        }
                    });
                }
            }).start();

            if (movie.getReleaseDate() != null) {
                textYear.setText(String.valueOf(movie.getReleaseDate().getYear()));
            } else if (movie.getFirstAirDate() != null) {
                textYear.setText(String.valueOf(movie.getFirstAirDate().getYear()));
            }
            StringBuilder genres = new StringBuilder();

            try {
                List<String> genreIds = movie.getGenreIds();
                if (genreIds != null && !genreIds.isEmpty()) {
                    genres.append(genreIds.get(0));
                    for (int i = 1; i < genreIds.size(); i++) {
                        genres.append(",");
                        genres.append(genreIds.get(i));
                    }
                }
            } catch (NullPointerException ignored) {
            }

            if (genres.length() == 0 && movie.getGenres() != null && !movie.getGenres().isEmpty()) {
                List<Genre> genresList = movie.getGenres();
                genres.append(genresList.get(0).getName());
                for (int i = 1; i < genresList.size(); i++) {
                    genres.append(",");
                    genres.append(genresList.get(i).getName());
                }
            }

            textGenres.setText(genres);


            imageMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieClickListener != null) {
                        int movieId = movie.getId();
                        onMovieClickListener.onMovieClick(movieId);
                    }
                }
            });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    exist = MPListApi.checkExistMovieInList(idList, movie.getId());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (exist) {
                                buttonAdd.setImageResource(R.drawable.delete_pink);
                            } else {
                                buttonAdd.setImageResource(R.drawable.plus_yellow);
                            }
                        }
                    });

                }
            }).start();

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (exist) {
                        buttonAdd.setImageResource(R.drawable.plus_yellow);
                    } else {
                        buttonAdd.setImageResource(R.drawable.delete_pink);
                    }
                    exist = !exist;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPListApi.setOrDelMovie(idList, movie.getId());
                        }
                    }).start();
                }
            });

        }
    }
}