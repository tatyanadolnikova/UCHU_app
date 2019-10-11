package com.example.android.uchu.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.uchu.PersonalInfoActivity;
import com.example.android.uchu.R;
import com.example.android.uchu.SearchActivity;
import com.example.android.uchu.User;
import com.example.android.uchu.ui.keyboard.KeyboardUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationFragment extends Fragment {

    private View root;
    private RegistrationViewModel registrationViewModel;
    private EditText email;
    private EditText password;
    private Button registerButton;

    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrationViewModel =
                ViewModelProviders.of(this).get(RegistrationViewModel.class);
        root = inflater.inflate(R.layout.fragment_registration, container, false);
        email = root.findViewById(R.id.reg_username);
        password = root.findViewById(R.id.reg_password);

        password.setImeActionLabel("Ok:)", EditorInfo.IME_ACTION_DONE);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createUser();
                    return true;
                }
                return false;
            }
        });

        registerButton = root.findViewById(R.id.reg_button);
        mAuth = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
        return root;
    }

    public void createUser() {
        final String email_text = email.getText().toString().trim();
        final String password_text = password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email_text, password_text)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User.setFirebaseUser(mAuth.getCurrentUser());
                            Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
                            startActivity(intent);
                        } else {
                            KeyboardUtils.hideKeyboard(getActivity());
                            Toast.makeText(getContext(), "Пользователь существует.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}