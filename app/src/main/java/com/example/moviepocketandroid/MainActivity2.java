package com.example.moviepocketandroid;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.databinding.ActivityMain2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private NavController navController;
    private int selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MPAuthenticationApi.setContext(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.userFragment
        ).build();

        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(item -> {
            // Check if the selected item is already the active one
            if (item.getItemId() == selectedItemId) {
                // If it's the active item, navigate to the start destination of the current graph
                navController.navigate(navController.getGraph().getStartDestination());

                // Additional logic to launch one of the specific fragments
                if (item.getItemId() == R.id.navigation_home) {
                    // Launch the Home fragment
                    navController.navigate(R.id.navigation_home);
                } else if (item.getItemId() == R.id.navigation_search) {
                    // Launch the Search fragment
                    navController.navigate(R.id.navigation_search);
                } else if (item.getItemId() == R.id.userFragment) {
                    // Launch the User fragment
                    navController.navigate(R.id.userFragment);
                } else if (item.getItemId() == R.id.feedFragment) {
                    // Launch the User fragment
                    navController.navigate(R.id.feedFragment);
                }
            } else {
                // Handle the item selection manually
                NavigationUI.onNavDestinationSelected(item, navController);
                selectedItemId = item.getItemId(); // Update the selected item ID
            }

            // Reset item colors
            navView.setItemIconTintList(null);
            navView.setItemTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.bottom_nav_item_color)));
            navView.setItemIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.bottom_nav_text_color)));

            return true;
        });
    }
}
