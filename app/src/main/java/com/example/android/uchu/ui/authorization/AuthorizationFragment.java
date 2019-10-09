package com.example.android.uchu.ui.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.uchu.R;

public class AuthorizationFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button authorizationButton;
    private Button iForgotPasswordButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModelProviders.of(this).get(AuthorizationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_authorization, container, false);

        usernameEditText = root.findViewById(R.id.auth_username);
        passwordEditText = root.findViewById(R.id.auth_password);
        authorizationButton = root.findViewById(R.id.enter_button);
        iForgotPasswordButton = root.findViewById(R.id.i_forgot_password_button);

        authorizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Входишь?", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(getActivity(), SearchActivity.class);
                //startActivity(intent);

            }
        });

        iForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Забыл пароль???", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }


}