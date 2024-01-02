package com.example.moviepocketandroid.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAuthenticationApi;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class loginFragment extends Fragment {

    private EditText editTextPassword, editTextUsername;
    private MaterialButton buttonLogin, buttonRegistration;
    private TextView textViewForgot;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    public static loginFragment newInstance() {
        return new loginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        buttonLogin = view.findViewById(R.id.buttonLogin);
        buttonRegistration = view.findViewById(R.id.buttonRegistration);

        textViewForgot = view.findViewById(R.id.textViewForgot);

        MPAuthenticationApi mpAuthenticationAPI = new MPAuthenticationApi();

        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_loginFragment_to_lostPasswordFragment);
            }

        });

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                navController.navigate(R.id.action_loginFragment_to_registrationFragment);
            }

        });

        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = Objects.requireNonNull(editTextUsername.getText()).toString();
                if (!hasFocus) {
                    if (!isValidEmail(email)) {
                        editTextUsername.setError("Enter a correct email");
                    }

                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextUsername.getError() == null) {
                    String username = String.valueOf(editTextUsername.getText());
                    String password = String.valueOf(editTextPassword.getText());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Boolean authentication = mpAuthenticationAPI.postLogin(username, password);
                            if (mContext != null) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (authentication) {
                                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                            navController.navigateUp();
                                            Toast.makeText(mContext, "Successfully!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, "Wrong password or username!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }).start();
                } else {
                    if (mContext != null) {
                        Toast.makeText(mContext, "Enter correct information", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}