package com.example.moviepocketandroid.ui.post.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.models.post.Post;
import com.example.moviepocketandroid.ui.until.AuthorAndRating;

public class PostMainFragment extends Fragment {

    private PostMainViewModel mViewModel;
    private int idPost;
    private Post post;
    private TextView textTitle;
    private TextView textContent;
    private View view;

    public PostMainFragment(int idPost) {
        Bundle args = new Bundle();
        args.putInt("idPost", idPost);
        setArguments(args);
    }

    public PostMainFragment() {
    }

    public static PostMainFragment newInstance() {
        return new PostMainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            idPost = args.getInt("idPost");

            textTitle = view.findViewById(R.id.textTitle);
            textContent = view.findViewById(R.id.textContent);
            this.view = view;

            loadPostInf();
        }

    }

    private void loadPostInf() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                post = MPPostApi.getPostById(idPost);
                if (post != null) {
                    if (isAdded()) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                setPostInf();
                            }
                        });
                    }
                }
            }
        }).start();

    }

    private void setPostInf() {
        textTitle.setText(post.getTitle());
        textContent.setText(post.getContent());
        AuthorAndRating authorAndRating = new AuthorAndRating(view, post.getId(), post.getUser(), post.getLikeOrDis());
        authorAndRating.setPostRatingButtons();
    }
}