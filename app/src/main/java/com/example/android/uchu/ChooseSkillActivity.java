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

import java.util.HashMap;
import java.util.Map;

public class ChooseSkillActivity extends AppCompatActivity {

    private Spinner skillSpinner1;
    private Spinner skillSpinner2;
    private Spinner skillSpinner3;
    private FloatingActionButton submitSkillButton;
    private Button addSecondSkill;
    private Button addThirdSkill;

    private String[] skillSet = new String[3];

    private DocumentReference mDocRef;
    //public static final String NO_SKILL = "no skill";
    public static String CHOOSE_SKILL;
    public static int SKILL_COUNTER = 0;

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
                    submitSkills();
                    if (SKILL_COUNTER > 0) {
                        Intent intent = new Intent(ChooseSkillActivity.this, SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case R.id.add_second_skill_button:
                    if (!defineDocumentName(skillSet[0]).equals(CHOOSE_SKILL)) {
                        addSecondSkill.setVisibility(View.INVISIBLE);
                        skillSpinner2.setVisibility(View.VISIBLE);
                        addThirdSkill.setVisibility(View.VISIBLE);
                        skillSpinner2.setOnTouchListener(onTouchListener);
                        setupSpinner(skillSpinner2);
                    }
                    break;
                case R.id.add_third_skill_button:
                    if (!defineDocumentName(skillSet[1]).equals(CHOOSE_SKILL)) {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_skill);
        CHOOSE_SKILL = getResources().getString(R.string.skill_zero);
        skillSet[0] = CHOOSE_SKILL;
        skillSet[1] = CHOOSE_SKILL;
        skillSet[2] = CHOOSE_SKILL;
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
                if (!TextUtils.isEmpty(selection)) {
                    switch (spinner.getId()) {
                        case R.id.choose_spinner_1:
                            skillSet[0] = selection;
                            Log.i("superproverka", "skillSet[0] = " + selection);
                            break;
                        case R.id.choose_spinner_2:
                            skillSet[1] = selection;
                            Log.i("superproverka", "skillSet[1] = " + selection);
                            break;
                        case R.id.choose_spinner_3:
                            skillSet[2] = selection;
                            Log.i("superproverka", "skillSet[2] = " + selection);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void submitSkills() {
        FirebaseUser user = User.getFirebaseUser();
        for (int i = 0; i < 3; i++) {
            String docName = defineDocumentName(skillSet[i]);
            Log.i("superproverka", "docName: " + docName);
            if (docName.equals(CHOOSE_SKILL)) return;
            SKILL_COUNTER++;
            Log.i("superproverka", "skillCounter: " + SKILL_COUNTER);
            mDocRef = FirebaseFirestore.getInstance().document("languages_sk/" + docName);
            Map<String, Object> data = new HashMap<>();
            data.put(user.getUid(), i);
            mDocRef.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.i("superproverka", "skill has been added");
                    } else {
                        Log.i("superproverka", "skill has NOT been added");
                    }
                }
            });
        }
    }

    private String defineDocumentName(String chosenSkill) {
        switch (chosenSkill) {
            case "Английский язык":
                return "en";
            case "Немецкий язык":
                return "ge";
            case "Французский язык":
                return "fr";
            case "Испанский язык":
                return "sp";
            case "Итальянский язык":
                return "it";
            case "Китайский язык":
                return "ch";
            case "Японский язык":
                return "jp";
            default:
                return CHOOSE_SKILL;
        }
    }

}
