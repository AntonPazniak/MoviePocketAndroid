package com.example.moviepocketandroid.ui.review.detail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.animation.Animation;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.models.review.Review;

public class DetailReviewFragment extends Fragment {

    private DetailReviewViewModel mViewModel;

    private TextView textTitle;
    private TextView textContent;
    private TextView textUsername;
    private TextView textDate;
    private ImageView trashImageView;
    private int idReview;
    private MPReviewApi mpReviewApi = new MPReviewApi();

    public static DetailReviewFragment newInstance() {
        return new DetailReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_review, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailReviewViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textUsername = view.findViewById(R.id.textViewUsername);
        textDate = view.findViewById(R.id.textViewDate);
        textTitle = view.findViewById(R.id.textViewTitle);
        textContent = view.findViewById(R.id.textViewContent);
        trashImageView = view.findViewById(R.id.imageViewTrash);

        trashImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trashImageView.startAnimation(Animation.createAnimation());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mpReviewApi.delReviewMovie(idReview)) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigateUp();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle args = getArguments();
                if (args != null) {
                    idReview = args.getInt("idReview");
                    Review review = mpReviewApi.getReviewById(idReview);
                    Boolean authorship = mpReviewApi.getAuthorship(review.getId());

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            textUsername.setText(review.getUsername());
                            textDate.setText(review.getDataCreated());
                            textTitle.setText(review.getTitle());
                            textContent.setText(review.getContent());
                            if (authorship) {
                                trashImageView.setVisibility(View.VISIBLE);
                            } else {
                                trashImageView.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        }).start();
    }

}