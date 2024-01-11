/*
 *
 *  * ******************************************************
 *  *  Copyright (C) MoviePocket <prymakdn@gmail.com>
 *  *  This file is part of MoviePocket.
 *  *  MoviePocket can not be copied and/or distributed without the express
 *  *  permission of Danila Prymak, Alexander Trafimchyk and Anton Pozniak
 *  * *****************************************************
 *
 */

package com.example.moviepocketandroid.ui.firstRun;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moviepocketandroid.MainActivity2;
import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ImagePagerAdapter;
import com.google.android.material.button.MaterialButton;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class FirstRunActivity extends AppCompatActivity {

    private MaterialButton buttonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        DotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.screenshot_0);
        images.add(R.drawable.screenshot_5);
        images.add(R.drawable.screenshot_1);
        images.add(R.drawable.screenshot_2);
        images.add(R.drawable.screenshot_3);
        images.add(R.drawable.screenshot_4);
        images.add(R.drawable.screenshot_8);
        images.add(R.drawable.screenshot_6);
        images.add(R.drawable.screenshot_7);


        ImagePagerAdapter adapter = new ImagePagerAdapter(this, images);
        viewPager.setAdapter(adapter);
        dotsIndicator.setViewPager2(viewPager);

        buttonSkip = findViewById(R.id.buttonSkip);
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstRunActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });


    }
}