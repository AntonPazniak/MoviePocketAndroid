package com.example.moviepocketandroid.ui.lostPasswoed;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LostPasswordFragment extends Fragment {

    private LostPasswordViewModel mViewModel;
    private MaterialButton buttonLostPas;
    private TextInputEditText editTextTextEmailAddress;

    public static LostPasswordFragment newInstance() {
        return new LostPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lost_password, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LostPasswordViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonLostPas = view.findViewById(R.id.buttonLostPas);
        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);

        editTextTextEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = Objects.requireNonNull(editTextTextEmailAddress.getText()).toString();
                if (!hasFocus) {
                    if (!isValidEmail(email)) {
                        editTextTextEmailAddress.setError("Enter a correct email");
                    }
                }
            }
        });

        buttonLostPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = String.valueOf(editTextTextEmailAddress.getText());

                if (editTextTextEmailAddress.getError() == null &&
                        !email.isEmpty() &&
                        isValidEmail(email)
                ) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MPAuthenticationApi mpAuthenticationApi = new MPAuthenticationApi();
                            mpAuthenticationApi.postLostPassword(email);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigateUp();
                                    Toast.makeText(requireContext(), "if a user with such an email exists, then we sent him a message to his email address", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(requireContext(), "Enter correct information", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}