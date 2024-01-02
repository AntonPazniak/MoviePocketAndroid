package com.example.moviepocketandroid.ui.list.description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.models.list.MovieList;

public class ListDescriptionFragment extends Fragment {

    private ListDescriptionViewModel mViewModel;
    private int idList;
    private MovieList movieList;

    public ListDescriptionFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }

    public ListDescriptionFragment() {
    }

    public static ListDescriptionFragment newInstance() {
        return new ListDescriptionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_description, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListDescriptionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            idList = args.getInt("idList");
        }
    }
}