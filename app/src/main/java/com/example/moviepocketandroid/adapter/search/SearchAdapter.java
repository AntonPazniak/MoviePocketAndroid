package com.example.moviepocketandroid.adapter.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.movie.Movie;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private SearchAdapter.OnMovieClickListener onMovieClickListener;


    public interface OnMovieClickListener {
        void onMovieClick(int movieId);
    }

    public void setOnMovieClickListener(SearchAdapter.OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    public SearchAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public SearchAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);

// Set desired width and height for the movie item (if needed)
        int desiredHeightDp = 110;
        float density = holder.itemView.getResources().getDisplayMetrics().density;
        int desiredHeightPx = (int) (desiredHeightDp * density);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = desiredHeightPx;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageMovie;
        private TextView textTitleMovie;
        private TextView textGenres;
        private TextView textYear;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.imagePosterMovieSearch);
            textTitleMovie = itemView.findViewById(R.id.textTitleMovieSearch);
            textYear = itemView.findViewById(R.id.textYear);
            textGenres = itemView.findViewById(R.id.textGenres);
        }

        public void bind(Movie movie) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .apply(requestOptions)
                    .into(imageMovie);
            textTitleMovie.setText(movie.getTitle());
            if (movie.getReleaseDate() != null) {
                int year = movie.getReleaseDate().getYear() + 1900;
                textYear.setText(String.valueOf(year));
            }
            StringBuilder genders = new StringBuilder();
            if (movie.getGenres() != null) {
                genders.append(movie.getGenres().get(0));
                for (int i = 1; i < movie.getGenres().size(); i++) {
                    genders.append(",");
                    genders.append(movie.getGenres().get(i));
                }
                textGenres.setText(genders);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieClickListener != null) {
                        int movieId = movie.getId();
                        onMovieClickListener.onMovieClick(movieId);
                    }
                }
            });
        }
    }
}