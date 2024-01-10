package com.example.moviepocketandroid.ui.search.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;

public class SearchListFragment extends Fragment {

    private SearchListViewModel mViewModel;
    private RecyclerView searchRecyclerView;

    public static SearchListFragment newInstance() {
        return new SearchListFragment();
    }

    public SearchListFragment() {
    }

    public SearchListFragment(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);
        setArguments(args);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
    }
}