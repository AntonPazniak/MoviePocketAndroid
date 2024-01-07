package com.example.moviepocketandroid.ui.feed.list;

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

public class FeedListFragment extends Fragment {

    private FeedListViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<MovieList> lists;

    public static FeedListFragment newInstance() {
        return new FeedListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FeedListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewLists);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lists = MPListApi.getLastLists();
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (lists != null && !lists.isEmpty()) {
                                ListAdapter2 listAdapter = new ListAdapter2(lists);
                                recyclerView.setAdapter(listAdapter);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager);

                                listAdapter.setOnItemClickListener(new ListAdapter2.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int idList) {
                                        Bundle args = new Bundle();
                                        args.putInt("idList", idList);
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_feedFragment_to_listFragment, args);
                                    }
                                });
                            }

                        }
                    });
                }
            }).start();
        } catch (IllegalStateException e) {
            onDestroy();
        }

    }
}