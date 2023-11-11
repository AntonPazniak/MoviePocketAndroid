package com.example.moviepocketandroid.adapter.search;

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
import com.example.moviepocketandroid.api.models.Actor;

import java.util.List;

public class ActorSearchAdapter extends RecyclerView.Adapter<ActorSearchAdapter.ActorViewHolder> {

    private List<Actor> actors;
    private OnActorClickListener onActorClickListener;

    public ActorSearchAdapter(List<Actor> actors) {
        this.actors = actors;
    }

    public void setOnActorClickListener(OnActorClickListener listener) {
        this.onActorClickListener = listener;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actors.get(position);
        holder.bind(actor);

        int desiredHeightDp = 110;
        float density = holder.itemView.getResources().getDisplayMetrics().density;
        int desiredHeightPx = (int) (desiredHeightDp * density);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = desiredHeightPx;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public interface OnActorClickListener {
        void onActorClick(int actorId);
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageMovie;
        private TextView textTitleMovie;
        private TextView textGenres;
        private TextView textYear;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.imagePosterMovieSearch);
            textTitleMovie = itemView.findViewById(R.id.textTitleMovieSearch);
            textYear = itemView.findViewById(R.id.textYear);
            textGenres = itemView.findViewById(R.id.textGenres);
        }

        public void bind(Actor actor) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(actor.getProfilePath())
                    .apply(requestOptions)
                    .into(imageMovie);
            textTitleMovie.setText(actor.getName());
            textYear.setText(actor.getBirthday());
            StringBuilder genders = new StringBuilder();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onActorClickListener != null) {
                        int actorId = actor.getId();
                        onActorClickListener.onActorClick(actorId);
                    }
                }
            });
        }
    }
}
