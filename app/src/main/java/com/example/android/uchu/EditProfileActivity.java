package com.example.android.uchu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.android.uchu.ui.dashboard.DashboardFragment;
import com.example.android.uchu.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText city;
    private EditText birthday;
    private FloatingActionButton fab;
    private Spinner skillSpinner1;
    private Spinner skillSpinner2;
    private Spinner skillSpinner3;
    private Button addSecondSkill;
    private Button addThirdSkill;

    private int skill_counter = 0;

    private ArrayList<String> skillSet = new ArrayList<>();

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.edit_name);
        surname = findViewById(R.id.edit_surname);
        city = findViewById(R.id.edit_city);
        birthday = findViewById(R.id.edit_birthday);

        name.setText(ProfileFragment.name);
        surname.setText(ProfileFragment.surname);
        city.setText(ProfileFragment.city);
        birthday.setText(ProfileFragment.birthday);

        skillSpinner1 = findViewById(R.id.edit_spinner_1);
        skillSpinner2 = findViewById(R.id.edit_spinner_2);
        skillSpinner3 = findViewById(R.id.edit_spinner_3);
        fab = findViewById(R.id.edit_button);

        fab.setOnClickListener(onClickListener);

        for (int i = 1; i <= ProfileFragment.skills.size(); i++) {
            if (i == 1) {
                skillSpinner1.setOnTouchListener(onTouchListener);
                setupSpinner(skillSpinner1);
                skillSpinner1.setSelection(SkillConverter.convertToIndex(ProfileFragment.skills.get(i - 1)));
                skill_counter++;
            } else if (i == 2) {
                skillSpinner2.setVisibility(View.VISIBLE);
                skillSpinner2.setOnTouchListener(onTouchListener);
                setupSpinner(skillSpinner2);
                skillSpinner2.setSelection(SkillConverter.convertToIndex(ProfileFragment.skills.get(i - 1)));
                skill_counter++;
            } else if (i == 3) {
                skillSpinner3.setVisibility(View.VISIBLE);
                skillSpinner3.setOnTouchListener(onTouchListener);
                setupSpinner(skillSpinner3);
                skillSpinner3.setSelection(SkillConverter.convertToIndex(ProfileFragment.skills.get(i - 1)));
                skill_counter++;
            }
        }
    }

    private void setupSpinner(final Spinner spinner) {
        ArrayAdapter importanceSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.skills, android.R.layout.simple_spinner_item);
        importanceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(importanceSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection) && !selection.equals(ChooseSkillActivity.CHOOSE_SKILL)) {
                    switch (spinner.getId()) {
                        case R.id.choose_spinner_1:
                            skillSet.add(0, SkillConverter.convertToShortSkill(selection));
                            break;
                        case R.id.choose_spinner_2:
                            skillSet.add(1, SkillConverter.convertToShortSkill(selection));
                            break;
                        case R.id.choose_spinner_3:
                            skillSet.add(2, SkillConverter.convertToShortSkill(selection));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (skill_counter > 0) {
                updateData(name.getText().toString().trim(), surname.getText().toString().trim(),
                        city.getText().toString().trim(), birthday.getText().toString().trim());
                Intent intent = new Intent(EditProfileActivity.this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
    };

    public void updateData(String name, String surname, String city, String birthday) {
        if (name.equals("") || surname.equals("") || city.equals("") || birthday.equals("")) {
            Log.i("superproverka", "Пустое поле");
            Toast.makeText(this, "Заполните все поля.", Toast.LENGTH_SHORT).show();
            return ;
        }
        Map<String, Object> data = new HashMap<>();
        data.put(PersonalInfoActivity.NAME_KEY, name);
        data.put(PersonalInfoActivity.SURNAME_KEY, surname);
        data.put(PersonalInfoActivity.CITY_KEY, city);
        data.put(PersonalInfoActivity.BIRTHDAY_KEY, birthday);
        Log.i("superproverka", "Добавляем имя " + name + ", фамилию " + surname + " и пр. ...");
        SearchActivity.usersDoc.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.i("superproverka", "doc has been saved");
                } else {
                    Log.i("superproverka", "doc has NOT been saved");
                }
            }
        });
    }
}
