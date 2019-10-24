package com.example.android.uchu;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private TextView birthdayTextView;
    private TextView cityTextView;
    private TextView skill1TextView;
    private TextView skill2TextView;
    private TextView skill3TextView;

    private String id;
    private String url;
    private String fullName;
    private String birthday;
    private String city;
    private ArrayList<String> skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        birthdayTextView = findViewById(R.id.user_birthday);
        cityTextView = findViewById(R.id.user_city);
        skill1TextView = findViewById(R.id.user_skill_1);
        skill2TextView = findViewById(R.id.user_skill_2);
        skill3TextView = findViewById(R.id.user_skill_3);
        setUserData();
    }

    private void setUserData() {
        Intent intent = getIntent();

        fullName = intent.getStringExtra("fullname");
        getSupportActionBar().setTitle(fullName);

        url = intent.getStringExtra("url");

        id = intent.getStringExtra("id");
        DocumentReference usersDoc = FirebaseFirestore.getInstance().document("users/" + id);
        usersDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                birthday = documentSnapshot.getString("birthday");
                birthdayTextView.setText(birthday);

                city = documentSnapshot.getString("city");
                cityTextView.setText(city);

                skills = (ArrayList<String>) documentSnapshot.get("skills");
                int skillCount = skills.size();
                for (int i = 0; i < skillCount; i++) {
                    if (i == 0)
                        skill1TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                    else if (i == 1)
                        skill2TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                    else if (i == 2)
                        skill3TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                }
            }
        });
    }

}
