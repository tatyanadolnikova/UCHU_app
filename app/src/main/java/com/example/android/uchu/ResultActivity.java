package com.example.android.uchu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity implements UserCardAdaptor.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserCardAdaptor userCardAdaptor;
    private ArrayList<UserCard> userList;
    private String chosenSkill;
    private Map<String, Object> map;
    private DocumentReference skillsDoc;
    private String fullName;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        chosenSkill = SkillConverter.convertToShortSkill(intent.getStringExtra("chosenSkill"));
        getSupportActionBar().setTitle("Поиск: " + intent.getStringExtra("chosenSkill"));
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findDataInFirebaseFirestore();
    }

    public void findDataInFirebaseFirestore() {
        map = new HashMap<>();
        userList = new ArrayList<>();
        fullName = "ALISA";
        skillsDoc = FirebaseFirestore.getInstance().document("languages_sk/" + chosenSkill);
        skillsDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot skillsSnapshot) {
                if (skillsSnapshot.exists()) {
                    map = skillsSnapshot.getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        i++;
                        DocumentReference userDoc = FirebaseFirestore.getInstance().document("users/" + entry.getKey());
                        userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot userSnapshot) {
                                fullName = userSnapshot.getString("name") + " "
                                        + userSnapshot.getString("surname");
                                userList.add(new UserCard("https://cdn.pixabay.com/photo/2019/09/13/15/13/chicken-4474176_1280.jpg", fullName));
                                if (i == map.size()) {
                                    userCardAdaptor = new UserCardAdaptor(ResultActivity.this, userList);
                                    recyclerView.setAdapter(userCardAdaptor);
                                    userCardAdaptor.setOnItemClickListener(ResultActivity.this);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "нажали на " + position + "карточку", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, UserActivity.class);
        UserCard clickedItem = userList.get(position);

        intent.putExtra("name", clickedItem.getuName());
        intent.putExtra("url", clickedItem.getImageUrl());

        startActivity(intent);
    }
}
