/*
 *
 *  * ******************************************************
 *  *  Copyright (C) MoviePocket <prymakdn@gmail.com>
 *  *  This file is part of MoviePocket.
 *  *  MoviePocket can not be copied and/or distributed without the express
 *  *  permission of Danila Prymak, Alexander Trafimchyk and Anton Pozniak
 *  * *****************************************************
 *
 */

package com.example.moviepocketandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;

public class RatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int EMPTY_ITEM = 0;
    private static final int RATING_ITEM = 1;

    private int[] ratings = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private OnRatingClickListener ratingClickListener;

    // Метод для установки слушателя
    public void setRatingClickListener(OnRatingClickListener listener) {
        this.ratingClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RATING_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_rating, parent, false);
            return new RatingViewHolder(view);
        } else {
            // Создаем ViewHolder для пустого элемента
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == RATING_ITEM) {
            int pos = ratings[position - 1]; // Смещение на один для обхода пустых элементов
            ((RatingViewHolder) holder).bind(pos);
        }
        // Иначе ничего не делаем, так как это пустой элемент
    }

    @Override
    public int getItemCount() {
        // Возвращаем общее количество элементов, включая пустые элементы
        return ratings.length + 2; // Два пустых элемента в начале и конце
    }

    @Override
    public int getItemViewType(int position) {
        // Определяем тип элемента (оценки или пустого элемента)
        if (position == 0 || position == getItemCount() - 1) {
            return EMPTY_ITEM;
        } else {
            return RATING_ITEM;
        }
    }

    // Интерфейс для обработки нажатий
    public interface OnRatingClickListener {
        void onRatingClick(int rating);
    }

    // ViewHolder для пустого элемента
    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // ViewHolder для элемента оценки
    public class RatingViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewRating;
        private Context context;

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            context = itemView.getContext();

            itemView.setOnClickListener(view -> {
                if (ratingClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        int rating = ratings[position - 1];
                        ratingClickListener.onRatingClick(rating);
                    }
                }
            });
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int rating) {
            if (rating < 4) {
                textViewRating.setTextColor(ContextCompat.getColor(context, R.color.logoPink));
                textViewRating.setText(String.valueOf(rating));
            } else if (rating < 8) {
                textViewRating.setTextColor(ContextCompat.getColor(context, R.color.logoBlue));
                textViewRating.setText(String.valueOf(rating));
            } else {
                textViewRating.setTextColor(ContextCompat.getColor(context, R.color.logoYellow));
                textViewRating.setText(String.valueOf(rating));
            }
        }
    }
}
