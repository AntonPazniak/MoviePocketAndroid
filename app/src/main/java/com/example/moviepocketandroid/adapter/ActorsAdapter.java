package com.example.moviepocketandroid.adapter;

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
import com.example.moviepocketandroid.api.models.Actor;

import java.util.List;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.ActorViewHolder> {

    private List<Actor> actors;
    private OnActorClickListener onActorClickListener;

    public interface OnActorClickListener {
        void onActorClick(int actorId);
    }

    public void setOnActorClickListener(OnActorClickListener listener) {
        this.onActorClickListener = listener;
    }

    public ActorsAdapter(List<Actor> actors) {
        this.actors = actors;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor, parent, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actors.get(position);
        holder.bind(actor);

        int desiredWidthDp = 120;
        int desiredHeightDp = 160;

        float density = holder.itemView.getResources().getDisplayMetrics().density;
        int desiredWidthPx = (int) (desiredWidthDp * density);
        int desiredHeightPx = (int) (desiredHeightDp * density);

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = desiredWidthPx;
        layoutParams.height = desiredHeightPx;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageActor;
        private TextView textNameActor;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageActor = itemView.findViewById(R.id.imageActor);
            textNameActor = itemView.findViewById(R.id.textNameActor);
        }

        public void bind(Actor actor) {
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(itemView.getContext())
                    .load(actor.getProfilePath())
                    .apply(requestOptions)
                    .into(imageActor);
            textNameActor.setText(actor.getName());

            // Set click listener for the actor item
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
