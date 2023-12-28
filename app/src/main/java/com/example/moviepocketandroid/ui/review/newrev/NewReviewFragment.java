package com.example.moviepocketandroid.ui.review.newrev;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPReviewApi;
import com.example.moviepocketandroid.api.models.review.Review;

public class NewReviewFragment extends Fragment {


    private EditText titleEditText;
    private MultiAutoCompleteTextView contentEditText;
    private Button publishButton;
    private TextView textViewHead, textViewTitle, textViewContent;

    public static NewReviewFragment newInstance() {
        return new NewReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_review, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleEditText = view.findViewById(R.id.editTextTitle);
        contentEditText = view.findViewById(R.id.multiTextContent);
        publishButton = view.findViewById(R.id.buttonPublish);
        textViewHead = view.findViewById(R.id.textViewHead);

        textViewTitle = view.findViewById(R.id.textView0);
        textViewContent = view.findViewById(R.id.textView1);

        textViewTitle.setText(R.string.title);
        textViewContent.setText(R.string.content);

        Bundle args = getArguments();
        if (args != null) {
            int idMovie = args.getInt("idMovie", -1);
            int idReview = args.getInt("idReview", -1);
            int idList = args.getInt("idList", -1);
            int idPost = args.getInt("idPost", -1);
            if (idMovie != -1) {
                newReviewMovie(idMovie);
            } else if (idReview != -1) {
                editReview(idReview);
            } else if (idList != -1) {
                newReviewList(idList);
            } else if (idPost != -1) {
                newReviewPost(idPost);
            }

        }
    }

    private void newReviewMovie(int idMovie) {
        textViewHead.setText(R.string.create_your_review);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPReviewApi.postReviewMovie(idMovie, title, content);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigateUp();
                            }
                        });
                    }
                }).start();

            }
        });
    }


    private void newReviewList(int idList) {
        textViewHead.setText(R.string.create_your_review);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPReviewApi.postReviewList(idList, title, content);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigateUp();
                            }
                        });
                    }
                }).start();

            }
        });
    }

    private void newReviewPost(int idList) {
        textViewHead.setText(R.string.create_your_review);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPReviewApi.postReviewPost(idList, title, content);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigateUp();
                            }
                        });
                    }
                }).start();

            }
        });
    }

    private void editReview(int idReview) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Review review = MPReviewApi.getReviewById(idReview);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        textViewHead.setText(R.string.edit_your_review);
                        titleEditText.setText(review.getTitle());
                        contentEditText.setText(review.getContent());
                    }
                });
            }
        }).start();

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MPReviewApi.editReviewMovie(idReview, title, content);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                navController.navigateUp();
                            }
                        });
                    }
                }).start();

            }
        });
    }

}