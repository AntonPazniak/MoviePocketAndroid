package com.example.moviepocketandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.user.User;
import com.example.moviepocketandroid.ui.until.UserInfoUntil;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private OnItemClickListener onItemClickListener;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String username);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewUsername;
        private final ImageView imageViewAvatar;
        private final Context context;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUsername = itemView.findViewById(R.id.textViewNickname);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            context = itemView.getContext();
        }

        public void bind(User user) {
            textViewUsername.setText(user.getUsername());
            UserInfoUntil.setUserInfo(user, context, imageViewAvatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(user.getUsername());
                    }
                }
            });
        }
    }
}
