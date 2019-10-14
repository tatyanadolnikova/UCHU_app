package com.example.android.uchu.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.uchu.EditProfileActivity;
import com.example.android.uchu.MainActivity;
import com.example.android.uchu.R;
import com.example.android.uchu.SearchActivity;
import com.example.android.uchu.SkillConverter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    public static String fullName;
    public static String name;
    public static String surname;
    public static String birthday;
    public static String city;
    public static ArrayList<String> skills;
    private ProfileViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView nameTextView = root.findViewById(R.id.profile_name);
        final TextView birthdayTextView = root.findViewById(R.id.profile_birthday);
        final TextView cityTextView = root.findViewById(R.id.profile_city);
        final TextView skill1TextView = root.findViewById(R.id.profile_skill_1);
        final TextView skill2TextView = root.findViewById(R.id.profile_skill_2);
        final TextView skill3TextView = root.findViewById(R.id.profile_skill_3);
        final FloatingActionButton button = root. findViewById(R.id.edit_button);

        SearchActivity.usersDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    name = documentSnapshot.getString("name");
                    surname = documentSnapshot.getString("surname");
                    fullName = name + " " + surname;
                    nameTextView.setText(fullName);
                    birthday = documentSnapshot.getString("birthday");
                    birthdayTextView.setText(birthday);
                    city = documentSnapshot.getString("city");
                    cityTextView.setText(city);
                    skills = (ArrayList<String>) documentSnapshot.get("skills");
                    int skillCount = skills.size();
                    for (int i = 0; i < skillCount; i++) {

                        if (i == 0) skill1TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                        else if (i == 1) skill2TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                        else if (i == 2) skill3TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}