package com.example.moviepocketandroid.ui.post.all;

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
import com.example.moviepocketandroid.adapter.PostAdapter;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.models.post.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAllFragment extends Fragment {

    private PostAllViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<Post> posts;

    public static PostAllFragment newInstance() {
        return new PostAllFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_all, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostAllViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idMovie", -1);
            int idPerson = args.getInt("idPost", -1);
            int idList = args.getInt("idList", -1);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    posts = new ArrayList<>();
                    if (idMovie != -1)
                        posts = MPPostApi.getAllPostExistIdMovie(idMovie);

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (posts != null && !posts.isEmpty()) {
                                PostAdapter postAdapter = new PostAdapter(posts);
                                recyclerView.setAdapter(postAdapter);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager);
                                postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int idPost) {
                                        Bundle args = new Bundle();
                                        args.putInt("idPost", idPost);
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_movieFragment_to_postFragment, args);
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