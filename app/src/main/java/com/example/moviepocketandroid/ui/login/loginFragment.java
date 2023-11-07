package com.example.moviepocketandroid.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviepocketandroid.R;
import com.example.moviepocketandroid.api.MP.MPAuthenticationAPI;

public class loginFragment extends Fragment {

    private EditText editTextPassword, editTextUsername;
    private Button buttonLogin;

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

        MPAuthenticationAPI mpAuthenticationAPI = new MPAuthenticationAPI();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = String.valueOf(editTextUsername.getText());
                String password = String.valueOf(editTextPassword.getText());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Boolean authentication = mpAuthenticationAPI.postLogin(username, password);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (authentication) {
                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main2);
                                        navController.navigateUp();
                                    } else {
                                        Toast.makeText(requireContext(), "Wrong password or username!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    }
                }).start();
            }
        });
    }

}