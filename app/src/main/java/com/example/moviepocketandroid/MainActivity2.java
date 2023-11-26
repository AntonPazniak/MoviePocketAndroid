package com.example.moviepocketandroid;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.moviepocketandroid.databinding.ActivityMain2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.userFragment
        ).build();

        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(item -> {
            // Handle the item selection manually
            NavigationUI.onNavDestinationSelected(item, navController);
            // Reset item colors
            navView.setItemIconTintList(null);
            navView.setItemTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.bottom_nav_item_color)));
            navView.setItemIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.bottom_nav_text_color)));

            return true;
        });
    }
}
