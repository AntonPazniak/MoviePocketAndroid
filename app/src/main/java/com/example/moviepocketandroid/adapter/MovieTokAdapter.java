package com.example.moviepocketandroid.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.example.moviepocketandroid.util.ButtonUntil;

import java.util.List;

public class MovieTokAdapter extends RecyclerView.Adapter<MovieTokAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MovieTokAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_tok, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPoster;
        private TextView textViewTitle;
        private TextView textViewCountry;
        private TextView textViewCategories;
        private TextView textViewReleaseDate;
        private TextView textViewOverview;
        private ImageView imageEye;
        private ImageView imageLike;
        private ImageView imageBackPack;
        private ButtonUntil buttonUntil;

        private boolean isExpanded = false;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewCountry = itemView.findViewById(R.id.textViewCountry);
            textViewCategories = itemView.findViewById(R.id.textViewCategories);
            textViewReleaseDate = itemView.findViewById(R.id.textViewReleaseDate);
            textViewOverview = itemView.findViewById(R.id.textViewOverview);
            imageEye = itemView.findViewById(R.id.imageEye);
            imageLike = itemView.findViewById(R.id.imageLike);
            imageBackPack = itemView.findViewById(R.id.imageBackPack);
            //buttonUntil = new ButtonUntil(imageEye, imageLike, imageBackPack,);
            textViewOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isExpanded) {
                        textViewOverview.setMaxLines(3);
                        textViewOverview.setEllipsize(TextUtils.TruncateAt.END);
                    } else {
                        textViewOverview.setMaxLines(Integer.MAX_VALUE);
                        textViewOverview.setEllipsize(null);
                    }
                    isExpanded = !isExpanded;
                }
            });

        }

        public void bind(Movie movie) {


            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .apply(requestOptions)
                    .into(imageViewPoster);
            textViewTitle.setText(movie.getTitle());
            textViewReleaseDate.setText(movie.getReleaseDate());
            textViewOverview.setText(movie.getOverview());
            StringBuilder s = new StringBuilder();
//            if (!movie.getProductionCountries().isEmpty()) {
//                s.append(movie.getProductionCountries().get(0));
//                for (int i = 1; i < movie.getProductionCountries().size(); i++) {
//                    s.append(", ");
//                    s.append(movie.getProductionCountries().get(i));
//                }
//                textViewCountry.setText(s);
//            }
            s = new StringBuilder();
            if (!movie.getGenres().isEmpty()) {
                s.append(movie.getGenres().get(0));
                for (int i = 1; i < movie.getGenres().size(); i++) {
                    s.append(", ");
                    s.append(movie.getGenres().get(i));
                }
                textViewCategories.setText(s);
            }
        }
    }
}
