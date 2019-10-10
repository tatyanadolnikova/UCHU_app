package com.example.android.uchu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChooseSkillActivity extends AppCompatActivity {

    private Spinner skillSpinner;
    private FloatingActionButton submitSkillButton;
    private String chosenSkill;

    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_skill);
        skillSpinner = findViewById(R.id.choose_spinner);
        submitSkillButton = findViewById(R.id.submit_skill_button);
        skillSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        setupSpinner(skillSpinner);
        submitSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = User.getFirebaseUser();
                mDocRef = FirebaseFirestore.getInstance().document("languages_sk/" + defineDocumentName());
                Map<String, Object> data = new HashMap<>();
                data.put(user.getUid(), 0);
                mDocRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.i("superproverka", "skill has been saved");
                            Intent intent = new Intent(ChooseSkillActivity.this, SearchActivity.class);
                            startActivity(intent);
                        } else {
                            Log.i("superproverka", "skill has NOT been saved");
                        }
                    }
                });
            }
        });
    }

    private void setupSpinner(Spinner spinner) {
        ArrayAdapter importanceSpinnerAdapter = ArrayAdapter.createFromResource(this,
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
                chosenSkill = getResources().getString(R.string.skill_zero);
            }
        });
    }

    private String defineDocumentName() {
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
                return "undefined skill";
        }
    }

}
