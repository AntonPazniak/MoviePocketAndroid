package com.example.moviepocketandroid.ui.search.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.ui.search.info.SearchInfoFragment;
import com.example.moviepocketandroid.ui.search.searchResults.SearchResultsFragment;
import com.google.android.material.appbar.MaterialToolbar;

public class SearchFragment extends Fragment {
    private SearchResultsFragment searchResultsFragment;
    private SearchInfoFragment childFragment;
    private MaterialToolbar toolbar;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        if (savedInstanceState == null) {
            // Начинаем транзакцию фрагмента
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // Создаем экземпляр ChildFragment
            childFragment = new SearchInfoFragment();

            // Заменяем фрагмент внутри ParentFragment на ChildFragment
            transaction.replace(R.id.fragmentContainer, childFragment);

            // Подтверждаем транзакцию
            transaction.commit();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.searchView);
        searchView = (SearchView) toolbar.getMenu().findItem(R.id.search_menu).getActionView();

        // Set up the OnQueryTextListener for the SearchView
        setupSearchView();
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
                    performSearch(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Handle text change (e.g., update search suggestions)
                    // You can perform some filtering or update suggestions here
                    return true;
                }
            });
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragmentContainer, childFragment);
//                    transaction.commit();
//                }
//            });
        }
    }

    private void performSearch(String query) {
        if (searchResultsFragment == null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            searchResultsFragment = new SearchResultsFragment(query);
            transaction.replace(R.id.fragmentContainer, searchResultsFragment);
            transaction.commit();
        } else {
            searchResultsFragment.updateQuery(query);
        }
    }

    private void setSearchBar() {
        // Assuming you have a reference to the SearchView

        // Set the background color
        // searchView.setBackgroundColor(ContextCompat.getColor(context, R.color.logoPink));

        // Find the EditText inside the SearchView
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        // Set text color and hint text color
        int textColor = getResources().getColor(R.color.logoPink); // Replace with your color resource
        editText.setTextColor(textColor);
        editText.setHintTextColor(textColor);

        // Set a custom hint text
        editText.setHint("Your Hint Text");

        // Set the query hint
        searchView.setQueryHint("Your Query Hint");
    }

}