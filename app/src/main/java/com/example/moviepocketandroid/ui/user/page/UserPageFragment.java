package com.example.moviepocketandroid.ui.user.page;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPUserApi;
import com.example.moviepocketandroid.api.models.user.UserPage;

public class UserPageFragment extends Fragment {

    private UserPageViewModel mViewModel;
    private TextView textViewUsername, textViewDate, textViewBio;

    public static UserPageFragment newInstance() {
        return new UserPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserPageViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewDate = view.findViewById(R.id.textViewDate);
        textViewBio = view.findViewById(R.id.textViewBio);


        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("username");
            loadUserDetails(username);
        }

    }

    private void loadUserDetails(String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserPage userPage = MPUserApi.getUserByUsername(username);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (userPage != null) {
                            textViewUsername.setText(userPage.getUsername());
                            textViewDate.setText(userPage.getCreated().toString());
                            textViewBio.setText(userPage.getBio());
                        }
                    }
                });
            }
        }).start();
    }

}