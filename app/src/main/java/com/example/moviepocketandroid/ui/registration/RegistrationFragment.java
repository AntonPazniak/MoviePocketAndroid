package com.example.moviepocketandroid.ui.registration;

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
import com.example.moviepocketandroid.util.PasswordValidator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel mViewModel;
    private MaterialButton buttonRegistration;
    private TextInputEditText editTextTextEmailAddress, editTextTextUsername, editTextTextPassword0, editTextTextPassword1;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextUsername = view.findViewById(R.id.editTextTextUsername);
        editTextTextPassword0 = view.findViewById(R.id.editTextTextPassword0);
        editTextTextPassword1 = view.findViewById(R.id.editTextTextPassword1);
        buttonRegistration = view.findViewById(R.id.buttonRegistration);

        MPAuthenticationApi mpAuthenticationApi = new MPAuthenticationApi();


        editTextTextEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = Objects.requireNonNull(editTextTextEmailAddress.getText()).toString();
                if (!hasFocus) {
                    if (!isValidEmail(email)) {
                        editTextTextEmailAddress.setError("Enter a correct email");
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Boolean exist = mpAuthenticationApi.existsUserByEmail(email);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (exist) {
                                            editTextTextEmailAddress.setError("This email address is already registered by another user");
                                        }
                                    }
                                });
                            }
                        }).start();
                    }

                }
            }
        });

        editTextTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String username = Objects.requireNonNull(editTextTextUsername.getText()).toString();
                if (!hasFocus) {
                    if (username.equals("")) {
                        editTextTextUsername.setError("Enter a correct username");
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Boolean exist = mpAuthenticationApi.existsUserByUsername(username);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (exist) {
                                            editTextTextUsername.setError("This username is already in use");
                                        }
                                    }
                                });
                            }
                        }).start();
                    }

                }
            }
        });

        editTextTextPassword0.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password = Objects.requireNonNull(editTextTextPassword0.getText()).toString();
                if (!PasswordValidator.isValidPassword(password)) {
                    editTextTextPassword0.setError("Password must be 8 or more characters in length.,Password must contain 1 or more uppercase characters.,Password must contain 1 or more digit characters.,Password must contain 1 or more special characters.");
                }
            }
        });

        editTextTextPassword1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password0 = Objects.requireNonNull(editTextTextPassword0.getText()).toString();
                String password1 = Objects.requireNonNull(editTextTextPassword1.getText()).toString();
                if (!password0.equals(password1)) {
                    editTextTextPassword1.setError("You repeated the first password incorrectly");
                }
            }
        });

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = String.valueOf(editTextTextEmailAddress.getText());
                String username = String.valueOf(editTextTextUsername.getText());
                String password = String.valueOf(editTextTextPassword0.getText());

                if (editTextTextEmailAddress.getError() == null &&
                        editTextTextUsername.getError() == null &&
                        editTextTextPassword0.getError() == null &&
                        editTextTextPassword1.getError() == null &&
                        !email.isEmpty() &&
                        !username.isEmpty() &&
                        !password.isEmpty()
                ) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Boolean isRegis = mpAuthenticationApi.postRegistration(username, email, password);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isRegis) {
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigateUp();
                                        Toast.makeText(requireContext(), "Successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
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