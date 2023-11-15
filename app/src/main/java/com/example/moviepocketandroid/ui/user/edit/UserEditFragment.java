package com.example.moviepocketandroid.ui.user.edit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.example.moviepocketandroid.api.MP.MPUserApi;
import com.example.moviepocketandroid.api.models.user.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class UserEditFragment extends Fragment {

    private UserEditViewModel mViewModel;

    private TextInputEditText editTextUsername, editTextEmailAddress;
    private MultiAutoCompleteTextView editTextBio;
    private MaterialButton buttonSave, buttonLogout;
    private User user;

    public static UserEditFragment newInstance() {
        return new UserEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserEditViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextEmailAddress = view.findViewById(R.id.editTextEmailAddress);
        editTextBio = view.findViewById(R.id.editTextBio);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        buttonSave = view.findViewById(R.id.buttonSave);


        new Thread(new Runnable() {
            @Override
            public void run() {
                user = MPUserApi.getUserInfo();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (user != null) {
                            editTextUsername.setText(user.getUsername());
                            editTextEmailAddress.setText(user.getEmail());
                            editTextBio.setText(user.getBio());
                        }
                    }
                });
            }
        }).start();

        setList();

    }


    private void setList() {

        MPAuthenticationApi mpAuthenticationApi = new MPAuthenticationApi();
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (MPAuthenticationApi.logout()) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                    navController.navigateUp();
                                    Toast.makeText(requireContext(), "Successfully logout!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }

        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextUsername.getError() == null &&
                        editTextEmailAddress.getError() == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String username = String.valueOf(editTextUsername.getText());
                            String bio = String.valueOf(editTextBio.getText());
                            String email = String.valueOf(editTextEmailAddress.getText());

                            if (!user.getUsername().equals(username))
                                MPUserApi.setUsername(username);
                            if (!user.getBio().equals(bio))
                                MPUserApi.setBio(bio);
                            if (!user.getEmail().equals(email))
                                MPUserApi.setEmail(email);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(requireContext(), "Save", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).start();
                }
            }

        });

        editTextEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = Objects.requireNonNull(editTextEmailAddress.getText()).toString();
                if (!hasFocus) {
                    if (!user.getEmail().equals(String.valueOf(editTextEmailAddress.getText()))) {
                        if (!isValidEmail(email)) {
                            editTextEmailAddress.setError("Enter a correct email");
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Boolean exist = mpAuthenticationApi.existsUserByEmail(email);
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (exist) {
                                                editTextEmailAddress.setError("This email address is already registered by another user");
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }

                    }
                }
            }
        });


        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String username = Objects.requireNonNull(editTextUsername.getText()).toString();
                if (!hasFocus) {
                    if (username.equals("")) {
                        editTextUsername.setError("Enter a correct username");
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Boolean exist = mpAuthenticationApi.existsUserByUsername(username);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (exist) {
                                            editTextUsername.setError("This username is already in use");
                                        }
                                    }
                                });
                            }
                        }).start();
                    }

                }
            }
        });

    }

    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}