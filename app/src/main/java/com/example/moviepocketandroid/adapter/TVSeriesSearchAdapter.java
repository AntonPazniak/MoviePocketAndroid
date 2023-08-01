package com.example.moviepocketandroid.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.tv.TVSeries;

import java.util.List;

public class TVSeriesSearchAdapter extends RecyclerView.Adapter<TVSeriesSearchAdapter.TVSeriesViewHolder> {

    private List<TVSeries> tvSeriesList;
    private OnTVSeriesClickListener onTVSeriesClickListener;

    public TVSeriesSearchAdapter(List<TVSeries> tvSeriesList) {
        this.tvSeriesList = tvSeriesList;
    }

    public void setOnTVSeriesClickListener(OnTVSeriesClickListener listener) {
        this.onTVSeriesClickListener = listener;
    }

    @NonNull
    @Override
    public TVSeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new TVSeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVSeriesViewHolder holder, int position) {
        TVSeries tvSeries = tvSeriesList.get(position);
        holder.bind(tvSeries);

        int desiredHeightDp = 110;
        float density = holder.itemView.getResources().getDisplayMetrics().density;
        int desiredHeightPx = (int) (desiredHeightDp * density);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = desiredHeightPx;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return tvSeriesList.size();
    }

    public interface OnTVSeriesClickListener {
        void onTVSeriesClick(int tvSeriesId);
    }

    public class TVSeriesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageMovie;
        private TextView textTitleMovie;
        private TextView textGenres;
        private TextView textYear;

        public TVSeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.imagePosterMovieSearch);
            textTitleMovie = itemView.findViewById(R.id.textTitleMovieSearch);
            textYear = itemView.findViewById(R.id.textYear);
            textGenres = itemView.findViewById(R.id.textGenres);
        }

        public void bind(TVSeries tvSeries) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(tvSeries.getPosterPath())
                    .apply(requestOptions)
                    .into(imageMovie);
            textTitleMovie.setText(tvSeries.getName());
            textYear.setText(tvSeries.getFirstAirDate());
            StringBuilder genders = new StringBuilder();
            if (!tvSeries.getGenres().isEmpty()) {
                genders.append(tvSeries.getGenres().get(0));
                for (int i = 1; i < tvSeries.getGenres().size(); i++) {
                    genders.append(",");
                    genders.append(tvSeries.getGenres().get(i));
                }
                textGenres.setText(genders);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTVSeriesClickListener != null) {
                        int movieId = tvSeries.getId();
                        onTVSeriesClickListener.onTVSeriesClick(movieId);
                    }
                }
            });
        }
    }
}
