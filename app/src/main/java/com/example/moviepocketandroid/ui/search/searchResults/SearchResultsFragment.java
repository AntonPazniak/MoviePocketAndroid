package com.example.moviepocketandroid.ui.search.searchResults;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.ui.search.frag.SearchRecyclerFragment;
import com.example.moviepocketandroid.adapter.ListNavBarAdapter;
import com.example.moviepocketandroid.ui.search.post.SearchPostFragment;
import com.google.android.material.tabs.TabLayout;

public class SearchResultsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ListNavBarAdapter listNavBarAdapter;

    private boolean isMovies = true;
    private boolean isTVs = false;
    private boolean isPersons = false;

    public SearchResultsFragment() {
    }

    public SearchResultsFragment(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);
        setArguments(args);
    }

    public static SearchResultsFragment newInstance() {
        return new SearchResultsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {

            String query = args.getString("query");

            tabLayout = view.findViewById(R.id.tabLayout);
            viewPager = view.findViewById(R.id.viewPager);

            tabLayout.setupWithViewPager(viewPager);

            listNavBarAdapter = new ListNavBarAdapter(getChildFragmentManager(),
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

            listNavBarAdapter.addFragmentAddTitle(new SearchRecyclerFragment(query), "Movies");
            listNavBarAdapter.addFragmentAddTitle(new SearchRecyclerFragment(query, 0), "TVs");
            listNavBarAdapter.addFragmentAddTitle(new SearchRecyclerFragment(query, 0, 0), "Actors");
            listNavBarAdapter.addFragmentAddTitle(new SearchPostFragment(query), "Posts");
            viewPager.setAdapter(listNavBarAdapter);

        }

    }

    public void updateQuery(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);
        setArguments(args);
        Fragment fragment = listNavBarAdapter.getItem(0);
        if (fragment instanceof SearchRecyclerFragment) {
            SearchRecyclerFragment yourFragment = (SearchRecyclerFragment) fragment;
            yourFragment.updateMovie(query);
        }
        fragment = listNavBarAdapter.getItem(1);
        if (fragment instanceof SearchRecyclerFragment) {
            SearchRecyclerFragment yourFragment = (SearchRecyclerFragment) fragment;
            yourFragment.updateTVs(query);
        }
        fragment = listNavBarAdapter.getItem(2);
        if (fragment instanceof SearchRecyclerFragment) {
            SearchRecyclerFragment yourFragment = (SearchRecyclerFragment) fragment;
            yourFragment.updatePersons(query);
        }
        fragment = listNavBarAdapter.getItem(3);
        if (fragment instanceof SearchPostFragment) {
            SearchPostFragment yourFragment = (SearchPostFragment) fragment;
            yourFragment.updateQuery(query);
        }
    }

}