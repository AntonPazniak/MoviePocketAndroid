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

package com.example.moviepocketandroid.ui.list.edit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.ui.review.newr.NewReviewFragment;
import com.google.android.material.button.MaterialButton;

public class ListEditFragment extends Fragment {

    private ListEditViewModel mViewModel;
    private NewReviewFragment newReviewFragment;

    public ListEditFragment() {
    }

    public ListEditFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }


    public static ListEditFragment newInstance() {
        return new ListEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListEditViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton button = view.findViewById(R.id.button);
        Bundle args = getArguments();
        if (args != null) {
            int idList = args.getInt("idList");
            if (savedInstanceState == null) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                // Создаем экземпляр ChildFragment
                newReviewFragment = new NewReviewFragment();
                newReviewFragment.setIdListEdit(idList);

                // Заменяем фрагмент внутри ParentFragment на ChildFragment
                transaction.replace(R.id.fragmentContainer, newReviewFragment);

                // Подтверждаем транзакцию
                transaction.commit();
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean isSuccessful = MPListApi.deleteList(idList);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccessful) {
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigateUp();
                                        Toast.makeText(requireContext(), "The list was deleted", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();
                }
            });

        }

    }


}