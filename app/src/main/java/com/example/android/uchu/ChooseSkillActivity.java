package com.example.android.uchu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseSkillActivity extends AppCompatActivity {

    private Spinner skillSpinner1;
    private Spinner skillSpinner2;
    private Spinner skillSpinner3;
    private FloatingActionButton submitSkillButton;
    private Button addSecondSkill;
    private Button addThirdSkill;

    private List<String> skillSet = new ArrayList<>();
    private ArrayList<String> skills;
    private DocumentReference skillsDoc;

    public static String CHOOSE_SKILL;

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.submit_skill_button:
                    if (skillSet.size() != 0) {
                        submitSkills();
                        Intent intent = new Intent(ChooseSkillActivity.this, SearchActivity.class);
                        intent.putExtra("reg", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();
                    }
                    break;
                case R.id.add_second_skill_button:
                    if (skillSet.size() == 1) {
                        addSecondSkill.setVisibility(View.INVISIBLE);
                        skillSpinner2.setVisibility(View.VISIBLE);
                        addThirdSkill.setVisibility(View.VISIBLE);
                        skillSpinner2.setOnTouchListener(onTouchListener);
                        setupSpinner(skillSpinner2);
                    }
                    break;
                case R.id.add_third_skill_button:
                    if (skillSet.size() == 2) {
                        addThirdSkill.setVisibility(View.INVISIBLE);
                        skillSpinner3.setVisibility(View.VISIBLE);
                        skillSpinner3.setOnTouchListener(onTouchListener);
                        setupSpinner(skillSpinner3);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("superproverka", "csa");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_skill);

        CHOOSE_SKILL = getResources().getString(R.string.skill_zero);

        skillSpinner1 = findViewById(R.id.choose_spinner_1);
        skillSpinner2 = findViewById(R.id.choose_spinner_2);
        skillSpinner3 = findViewById(R.id.choose_spinner_3);

        addSecondSkill = findViewById(R.id.add_second_skill_button);
        addThirdSkill = findViewById(R.id.add_third_skill_button);
        submitSkillButton = findViewById(R.id.submit_skill_button);

        skillSpinner1.setOnTouchListener(onTouchListener);
        setupSpinner(skillSpinner1);
        addSecondSkill.setOnClickListener(onClickListener);
        addThirdSkill.setOnClickListener(onClickListener);
        submitSkillButton.setOnClickListener(onClickListener);
    }

    private void setupSpinner(final Spinner spinner) {
        if (skills == null) skills = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.skills)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, skills);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection) && !selection.equals(CHOOSE_SKILL)) {
                    switch (spinner.getId()) {
                        case R.id.choose_spinner_1:
                            skillSet.add(0, SkillConverter.convertToShortSkill(selection));
                            skills.remove(position);
                            Log.i("superproverka", "skillSet 0 = " + SkillConverter.convertToShortSkill(selection));
                            break;
                        case R.id.choose_spinner_2:
                            skillSet.add(1, SkillConverter.convertToShortSkill(selection));
                            skills.remove(position);
                            Log.i("superproverka", "skillSet 1 = " + SkillConverter.convertToShortSkill(selection));
                            break;
                        case R.id.choose_spinner_3:
                            skillSet.add(2, SkillConverter.convertToShortSkill(selection));
                            skills.remove(position);
                            Log.i("superproverka", "skillSet 2 = " + SkillConverter.convertToShortSkill(selection));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void submitSkills() {
        Log.i("superproverka", "submitting skills");
        Map<String, Object> userSkills = new HashMap<>();
        userSkills.put("skills", skillSet);
        PersonalInfoActivity.usersDoc.update(userSkills).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i("superproverka", "userSkills saved in users db");
                } else {
                    Log.i("superproverka", "userSkills  NOT saved in users db");
                }
            }
        });

        for (String skillName : skillSet) {
            if (skillName.equals(CHOOSE_SKILL)) return;
            skillsDoc = FirebaseFirestore.getInstance().document("skills/" + skillName);
            Map<String, Object> data = new HashMap<>();
            data.put(PersonalInfoActivity.user.getUid(), FirebaseFirestore.getInstance().document("users/" + PersonalInfoActivity.user.getUid()));
            skillsDoc.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i("superproverka", "skillDoc updated");
                    } else {
                        Log.i("superproverka", "skillDoc NOT updated");
                    }
                }
            });
        }
    }

}
