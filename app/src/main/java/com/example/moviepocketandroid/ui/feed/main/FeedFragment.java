package com.example.moviepocketandroid.ui.feed.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListNavBarAdapter;
import com.example.moviepocketandroid.ui.feed.list.FeedListFragment;
import com.example.moviepocketandroid.ui.feed.post.FeedPostFragment;
import com.google.android.material.tabs.TabLayout;

public class FeedFragment extends Fragment {

    private FeedViewModel mViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ListNavBarAdapter listNavBarAdapter;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FeedViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        listNavBarAdapter = new ListNavBarAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        listNavBarAdapter.addFragmentAddTitle(new FeedListFragment(), "Lists");
        listNavBarAdapter.addFragmentAddTitle(new FeedPostFragment(), "Posts");
        viewPager.setAdapter(listNavBarAdapter);
    }
}