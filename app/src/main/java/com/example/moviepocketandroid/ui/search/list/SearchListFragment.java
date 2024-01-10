package com.example.moviepocketandroid.ui.search.list;

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
import com.example.moviepocketandroid.adapter.ListAdapter2;
import com.example.moviepocketandroid.api.MP.MPListApi;
import com.example.moviepocketandroid.api.models.list.MovieList;

import java.util.List;

public class SearchListFragment extends Fragment {

    private SearchListViewModel mViewModel;
    private RecyclerView searchRecyclerView;
    private List<MovieList> lists;

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
        Bundle args = getArguments();
        if (args != null) {
            String query = args.getString("query");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lists = MPListApi.getSearchList(query);
                    setInfo();
                }
            }).start();
        }
    }

    private void setInfo() {

        if (isAdded() && lists != null && !lists.isEmpty()) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListAdapter2 listAdapter = new ListAdapter2(lists);
                    searchRecyclerView.setAdapter(listAdapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

                    searchRecyclerView.setLayoutManager(layoutManager);
                    listAdapter.setOnItemClickListener(new ListAdapter2.OnItemClickListener() {
                        @Override
                        public void onItemClick(int idList) {
                            Bundle args = new Bundle();
                            args.putInt("idList", idList);
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_navigation_search_to_listFragment, args);
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
                lists = MPListApi.getSearchList(query);
                setInfo();
            }
        }).start();
    }
}