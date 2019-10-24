package com.example.android.uchu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private UserCardAdaptor userCardAdaptor;
    private ArrayList<UserCard> userList;
    private String chosenSkill;
    private Map<String, Object> map;
    private DocumentReference skillsDoc;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        progressBar = findViewById(R.id.search_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        chosenSkill = SkillConverter.convertToShortSkill(intent.getStringExtra("chosenSkill"));
        getSupportActionBar().setTitle("Поиск: " + intent.getStringExtra("chosenSkill"));
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findDataInFirebaseFirestore();
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void findDataInFirebaseFirestore() {
        map = new HashMap<>();
        userList = new ArrayList<>();
        skillsDoc = FirebaseFirestore.getInstance().document("skills/" + chosenSkill);

        skillsDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.i("superproverka", documentSnapshot.getData().toString());
                Map<String, Object> map = documentSnapshot.getData();
                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    final String userId = entry.getKey();
                    if (!userId.equals(User.getFirebaseUser().getUid())) {
                        DocumentReference mRef = (DocumentReference) entry.getValue();
                        mRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String name = documentSnapshot.getString("name")
                                            + " "
                                            + documentSnapshot.getString("surname");
                                    userList.add(new UserCard("http://st.gde-fon.com/wallpapers_original/342238_rebyonok_devochka_xvostiki_ulybka_radost_5000x3333_www.Gde-Fon.com.jpg", name, userId));
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
        Intent intent = new Intent(this, UserActivity.class);
        UserCard clickedItem = userList.get(position);
        Log.i("superproverka", "User " + clickedItem.getuName() + " is selected");
        Log.i("superproverka", "User id: " + clickedItem.getUserId());
        intent.putExtra("fullname", clickedItem.getuName());
        intent.putExtra("url", clickedItem.getImageUrl());
        intent.putExtra("id", clickedItem.getUserId());
        startActivity(intent);
    }
}
