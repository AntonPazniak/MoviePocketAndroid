package com.example.moviepocketandroid.ui.list.all;

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

public class ListAllFragment extends Fragment {

    private ListAllViewModel mViewModel;
    private RecyclerView recyclerViewMovie;
    private List<MovieList> lists;
    private int idMovie;

    public static ListAllFragment newInstance() {
        return new ListAllFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_all, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ListAllViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewMovie = view.findViewById(R.id.recyclerViewMovie);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idMovie");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    lists = MPListApi.getAllListExistIdMovie(idMovie);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (lists != null && !lists.isEmpty()) {
                                ListAdapter2 listAdapter2 = new ListAdapter2(lists);
                                recyclerViewMovie.setAdapter(listAdapter2);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerViewMovie.setLayoutManager(layoutManager);

                                listAdapter2.setOnItemClickListener(new ListAdapter2.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int idList) {
                                        Bundle args = new Bundle();
                                        args.putInt("idList", idList);
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_listAllFragment_to_listFragment, args);
                                    }
                                });
                            }

                        }
                    });
                }
            }).start();


        }
    }
}