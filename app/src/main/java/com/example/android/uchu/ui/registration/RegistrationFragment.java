package com.example.android.uchu.ui.registration;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.uchu.R;
import com.example.android.uchu.ui.DateMask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText password;
    private EditText city;
    private EditText birthday;
    private Spinner skill;
    private Button registerButton;

    private String chosenSkill;
    private boolean changesAllowed = false;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrationViewModel =
                ViewModelProviders.of(this).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        name = root.findViewById(R.id.reg_name);
        surname = root.findViewById(R.id.reg_surname);
        email = root.findViewById(R.id.reg_email);
        password = root.findViewById(R.id.reg_password);
        city = root.findViewById(R.id.reg_city);
        birthday = root.findViewById(R.id.reg_birthday);
        skill = root.findViewById(R.id.reg_spinner);
        registerButton = root.findViewById(R.id.reg_button);
        skill.setOnTouchListener(touchListener);
        setupSpinner(skill);

        birthday.addTextChangedListener(new DateMask(new DateMask.DateMaskingCallback() {
            @Override
            public void dateOfBirthValidation(boolean isValid, String dateOfBirth, String error) throws Exception {
                if (isValid) {
                    birthday.setText(dateOfBirth);
                    birthday.setSelection(dateOfBirth.length());
                } else {
                    birthday.setError(error);
                }
            }
        }, "."));

        return root;
    }

    private void setupSpinner(Spinner spinner) {
        ArrayAdapter importanceSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.skills, android.R.layout.simple_spinner_item);
        importanceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(importanceSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    chosenSkill = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                chosenSkill = "Выбрать навык";
            }
        });
    }
}