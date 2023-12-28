package com.example.moviepocketandroid.ui.post;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListNavBarAdapter;
import com.example.moviepocketandroid.api.MP.MPPostApi;
import com.example.moviepocketandroid.ui.list.edit.ListEditFragment;
import com.example.moviepocketandroid.ui.post.main.PostMainFragment;
import com.example.moviepocketandroid.ui.review.all.AllReviewFragment;
import com.google.android.material.tabs.TabLayout;

public class PostFragment extends Fragment {

    private PostViewModel mViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int idPost;


    public static PostFragment newInstance() {
        return new PostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            idPost = args.getInt("idPost");

            tabLayout = view.findViewById(R.id.tabLayout);
            viewPager = view.findViewById(R.id.viewPager);

            tabLayout.setupWithViewPager(viewPager);

            ListNavBarAdapter listNavBarAdapter = new ListNavBarAdapter(getChildFragmentManager(),
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Boolean authorship = MPPostApi.getAuthorship(idPost);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listNavBarAdapter.addFragmentAddTitle(new PostMainFragment(idPost), "Post");
                            listNavBarAdapter.addFragmentAddTitle(new AllReviewFragment(idPost), "Reviews");
                            if (authorship)
                                listNavBarAdapter.addFragmentAddTitle(new ListEditFragment(), "Edit");
                            viewPager.setAdapter(listNavBarAdapter);
                        }
                    });

                }
            }).start();


        }
    }
}