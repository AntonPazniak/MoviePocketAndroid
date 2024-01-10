package com.example.moviepocketandroid.ui.search.post;

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
import com.example.moviepocketandroid.adapter.PostAdapter2;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.api.models.post.Post;

import java.util.List;

public class SearchPostFragment extends Fragment {

    private SearchPostViewModel mViewModel;
    private RecyclerView searchRecyclerView;
    private List<Post> posts;
    private PostAdapter2 postAdapter2;

    public SearchPostFragment() {
    }

    public SearchPostFragment(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);
        setArguments(args);
    }

    public static SearchPostFragment newInstance() {
        return new SearchPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchPostViewModel.class);
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
                    posts = MPPostApi.getSearchPosts(query);
                    setInfo();
                }
            }).start();
        }

    }

    private void setInfo() {

        if (isAdded() && posts != null && !posts.isEmpty()) {
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PostAdapter2 postAdapter = new PostAdapter2(posts);
                    searchRecyclerView.setAdapter(postAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    searchRecyclerView.setLayoutManager(layoutManager);
                    postAdapter.setOnItemClickListener(new PostAdapter2.OnItemClickListener() {
                        @Override
                        public void onItemClick(int idPost) {
                            Bundle args = new Bundle();
                            args.putInt("idPost", idPost);
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                            navController.navigate(R.id.action_navigation_search_to_postFragment, args);
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
                posts = MPPostApi.getSearchPosts(query);
                setInfo();
            }
        }).start();
    }
}