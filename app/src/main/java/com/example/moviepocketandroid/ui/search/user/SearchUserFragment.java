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

package com.example.moviepocketandroid.ui.search.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.UserAdapter;
import com.example.moviepocketandroid.api.MP.MPUserApi;
import com.example.moviepocketandroid.api.models.user.User;

import java.util.List;

public class SearchUserFragment extends Fragment {

    private SearchUserViewModel mViewModel;
    private RecyclerView searchRecyclerView;
    private List<User> users;

    public SearchUserFragment() {
    }

    public SearchUserFragment(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);
        setArguments(args);
    }


    public static SearchUserFragment newInstance() {
        return new SearchUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchUserViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        Bundle args = getArguments();
        if (args != null) {
            String query = args.getString("query");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    users = MPUserApi.getSearchUserByUsername(query);
                    setInfo();
                }
            }).start();
        }
    }

    private void setInfo() {

        if (isAdded() && users != null && !users.isEmpty()) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UserAdapter userAdapter = new UserAdapter(users);
                    searchRecyclerView.setAdapter(userAdapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                    searchRecyclerView.setLayoutManager(layoutManager);
                    userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(String username) {
                            Bundle args = new Bundle();
                            args.putString("username", username);
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_navigation_search_to_userPageFragment, args);
                        }
                    });

                }
            });
        }

    }

    public void updateQuery(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);
        setArguments(args);
        new Thread(new Runnable() {
            @Override
            public void run() {
                users = MPUserApi.getSearchUserByUsername(query);
                setInfo();
            }
        }).start();
    }
}