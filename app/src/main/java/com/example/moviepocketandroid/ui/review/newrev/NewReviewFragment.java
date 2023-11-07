package com.example.moviepocketandroid.ui.review.newrev;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPReviewApi;

public class NewReviewFragment extends Fragment {


    private EditText titleEditText;
    private MultiAutoCompleteTextView contentEditText;
    private Button publishButton;

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

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(titleEditText.getText());
                String content = String.valueOf(contentEditText.getText());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle args = getArguments();
                        if (args != null) {
                            int idMovie = args.getInt("idMovie");
                            MPReviewApi mpReviewApi = new MPReviewApi();
                            mpReviewApi.postReviewMovie(idMovie, title, content);
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

    }


}