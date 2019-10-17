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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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

    private FirebaseUser user;
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.add_second_skill_button:
                    if (!skillSet.get(0).equals(CHOOSE_SKILL)) {
                        addSecondSkill.setVisibility(View.INVISIBLE);
                        skillSpinner2.setVisibility(View.VISIBLE);
                        addThirdSkill.setVisibility(View.VISIBLE);
                        skillSpinner2.setOnTouchListener(onTouchListener);
                        setupSpinner(skillSpinner2);
                    }
                    break;
                case R.id.add_third_skill_button:
                    if (!skillSet.get(1).equals(CHOOSE_SKILL)) {
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
        user = User.getFirebaseUser();

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
        ArrayAdapter importanceSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.skills, android.R.layout.simple_spinner_item);
        importanceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(importanceSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection) && !selection.equals(CHOOSE_SKILL)) {
                    switch (spinner.getId()) {
                        case R.id.choose_spinner_1:
                            skillSet.add(0, SkillConverter.convertToShortSkill(selection));
                            Log.i("superproverka", "skillSet 0 = " + SkillConverter.convertToShortSkill(selection));
                            break;
                        case R.id.choose_spinner_2:
                            skillSet.add(1, SkillConverter.convertToShortSkill(selection));
                            Log.i("superproverka", "skillSet 1 = " + SkillConverter.convertToShortSkill(selection));
                            break;
                        case R.id.choose_spinner_3:
                            skillSet.add(2, SkillConverter.convertToShortSkill(selection));
                            Log.i("superproverka", "skillSet 2 = " + SkillConverter.convertToShortSkill(selection));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void submitSkills() {
        Log.i("superproverka", "submitting skills");
        Map<String, Object> userSkills = new HashMap<>();
        userSkills.put("skills", skillSet);
        PersonalInfoActivity.usersDoc.update(userSkills).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.i("superproverka", "userSkills saved in users db");
                } else {
                    Log.i("superproverka", "userSkills  NOT saved in users db");
                }
            }
        });

        for (String skillName : skillSet) {
            if (skillName.equals(CHOOSE_SKILL)) return;
            skillsDoc = FirebaseFirestore.getInstance().document("skills/" + skillName);
            Log.i("superproverka", "skillDoc is found");

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
