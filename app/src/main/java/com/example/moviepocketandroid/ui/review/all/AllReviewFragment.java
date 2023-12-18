package com.example.moviepocketandroid.ui.review.all;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.adapter.ReviewAdapter;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.models.review.Review;

import java.util.List;

public class AllReviewFragment extends Fragment {

    private ReviewAdapter reviewAdapter;
    private RecyclerView recyclerView;
    private TextView titleTextView;

    public static AllReviewFragment newInstance() {
        return new AllReviewFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        View view1 = view.findViewById(R.id.item_text);
        titleTextView = view1.findViewById(R.id.textView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle args = getArguments();
                if (args != null) {
                    int idMovie = args.getInt("idMovie");
                    List<Review> reviews = MPReviewApi.getReviewAllByIdMovie(idMovie);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (reviews.size() > 0) {
                                reviewAdapter = new ReviewAdapter(reviews);
                                recyclerView.setAdapter(reviewAdapter);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager2);
                                reviewAdapter.setOnReviewClickListener(new ReviewAdapter.OnReviewClickListener() {
                                    @Override
                                    public void onReviewClick(int idReview) {
                                        Bundle args = new Bundle();
                                        args.putInt("idReview", idReview);

                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigate(R.id.action_allReviewFragment_to_detailReviewFragment, args);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_review, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }


}