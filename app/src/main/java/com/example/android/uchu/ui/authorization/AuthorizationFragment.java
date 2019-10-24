package com.example.android.uchu.ui.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.uchu.R;
import com.example.android.uchu.SearchActivity;
import com.example.android.uchu.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthorizationFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button authorizationButton;
    private Button iForgotPasswordButton;
    private ProgressBar progressBar;
    private View root;

    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModelProviders.of(this).get(AuthorizationViewModel.class);
        root = inflater.inflate(R.layout.fragment_authorization, container, false);
        mAuth = FirebaseAuth.getInstance();
        usernameEditText = root.findViewById(R.id.auth_username);
        passwordEditText = root.findViewById(R.id.auth_password);
        authorizationButton = root.findViewById(R.id.enter_button);
        iForgotPasswordButton = root.findViewById(R.id.i_forgot_password_button);

        passwordEditText.setImeActionLabel("Ok:)", EditorInfo.IME_ACTION_DONE);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signIn(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        authorizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(usernameEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
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

    @Override
    public void onStart() {
        super.onStart();
    }

    public void signIn(String email, String password) {
        Log.i("superproverka", "метод signIn");
        progressBar = root.findViewById(R.id.auth_progressbar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User.setFirebaseUser(mAuth.getCurrentUser());
                            Log.i("superproverka", "signIn: Успешная аутентификация");
                            Intent intent = new Intent(getActivity(), SearchActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("auth", true);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            Log.i("superproverka", "signIn: Вход не выполнен");
                            Toast.makeText(getActivity(), "Пользователь не найден.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}