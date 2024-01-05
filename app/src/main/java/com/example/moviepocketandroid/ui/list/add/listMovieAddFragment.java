package com.example.moviepocketandroid.ui.list.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ListNavBarAdapter;
import com.example.moviepocketandroid.ui.list.add.search.ListMovieAddSearchFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class listMovieAddFragment extends Fragment {

    private ListMovieAddViewModel mViewModel;
    private int idList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    private MaterialToolbar toolbar;
    private ListNavBarAdapter listNavBarAdapter;

    public listMovieAddFragment(int idList) {
        Bundle args = new Bundle();
        args.putInt("idList", idList);
        setArguments(args);
    }

    public listMovieAddFragment() {
    }

    public static listMovieAddFragment newInstance() {
        return new listMovieAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_movie_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.searchView);
        searchView = (SearchView) toolbar.getMenu().findItem(R.id.search_menu).getActionView();


        Bundle args = getArguments();
        if (args != null) {
            idList = args.getInt("idList");

            tabLayout = view.findViewById(R.id.tabLayout);
            viewPager = view.findViewById(R.id.viewPager);

            tabLayout.setupWithViewPager(viewPager);

            listNavBarAdapter = new ListNavBarAdapter(getChildFragmentManager(),
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            ListMovieAddSearchFragment movieFragment = new ListMovieAddSearchFragment(idList);
            listNavBarAdapter.addFragmentAddTitle(movieFragment, "Movie");
            ListMovieAddSearchFragment tvFragment = new ListMovieAddSearchFragment(idList);
            listNavBarAdapter.addFragmentAddTitle(tvFragment, "TVs");

            viewPager.setAdapter(listNavBarAdapter);

            setupSearchView();
        }
    }


    private void setupSearchView() {
        if (searchView instanceof SearchView) {
            SearchView sv = (SearchView) searchView;

            // Set the hint for the SearchView
            sv.setQueryHint("Search...");

            // Set the OnQueryTextListener
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Handle search submission (e.g., perform search based on query)
                    updateFragments(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Handle text change (e.g., update search suggestions)
                    // You can perform some filtering or update suggestions here
                    return true;
                }
            });
        }

    }

    private void updateFragments(String query) {
        Fragment movieFragment = listNavBarAdapter.getItem(0);
        if (movieFragment instanceof ListMovieAddSearchFragment) {
            ((ListMovieAddSearchFragment) movieFragment).updateMovie(query);
        }

        Fragment tvFragment = listNavBarAdapter.getItem(1);
        if (tvFragment instanceof ListMovieAddSearchFragment) {
            ((ListMovieAddSearchFragment) tvFragment).updateTVs(query);
        }
    }

}