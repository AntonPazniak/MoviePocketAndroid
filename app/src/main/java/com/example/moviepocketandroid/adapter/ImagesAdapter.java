package com.example.moviepocketandroid.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.MovieImage;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MovieImageViewHolder> {

    private List<MovieImage> movieImages;
    private List<Integer> imageWidths;
    private List<Integer> imageHeights;

    public ImagesAdapter(List<MovieImage> movieImages) {
        this.movieImages = movieImages;
        imageWidths = new ArrayList<>();
        imageHeights = new ArrayList<>();
    }

    @NonNull
    @Override
    public MovieImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_image, parent, false);
        return new MovieImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieImageViewHolder holder, int position) {
        MovieImage movieImage = movieImages.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(movieImage.getFilePath())
                .apply(requestOptions)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int imageWidthInDp = 110;
                        if (movieImage.getWidth()>movieImage.getHeight())
                            imageWidthInDp = 270;

                        int imageWidth = dpToPx(imageWidthInDp);
                        int imageHeight = dpToPx(160);
                        imageWidths.add(imageWidth);

                        ViewGroup.LayoutParams layoutParams = holder.imageViewMovie.getLayoutParams();
                        layoutParams.width = imageWidth;
                        layoutParams.height = imageHeight;
                        holder.imageViewMovie.setLayoutParams(layoutParams);

                        Glide.with(holder.itemView.getContext())
                                .load(movieImage.getFilePath())
                                .apply(requestOptions)
                                .into(holder.imageViewMovie);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Здесь можно выполнить действия при очистке изображения
                    }
                });
    }
    private int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


    @Override
    public int getItemCount() {
        return movieImages.size();
    }

    public class MovieImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewMovie;

        public MovieImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMovie = itemView.findViewById(R.id.imageViewMovie0);
        }
    }
}
