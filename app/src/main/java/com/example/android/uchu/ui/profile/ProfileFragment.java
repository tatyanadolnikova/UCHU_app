package com.example.android.uchu.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.android.uchu.EditProfileActivity;
import com.example.android.uchu.MainActivity;
import com.example.android.uchu.R;
import com.example.android.uchu.Request;
import com.example.android.uchu.SearchActivity;
import com.example.android.uchu.SkillConverter;
import com.example.android.uchu.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    public static String fullName;
    public static String name;
    public static String surname;
    public static String birthday;
    public static String city;
    public static ArrayList<String> skills;

    private View root;
    public ProfileViewModel homeViewModel;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        setHasOptionsMenu(true);
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView fullNameView = root.findViewById(R.id.profile_name);
        final TextView cityTextView = root.findViewById(R.id.profile_city);
        final ImageButton profileImage = root.findViewById(R.id.profile_image);

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("user_image/" + User.getFirebaseUser().getUid());
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(profileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Glide.with(getActivity()).load(R.drawable.man);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });

        final TextView skill1TextView = root.findViewById(R.id.profile_skill_1);
        final TextView skill2TextView = root.findViewById(R.id.profile_skill_2);
        final TextView skill3TextView = root.findViewById(R.id.profile_skill_3);

        SearchActivity.usersDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    name = documentSnapshot.getString("name");
                    surname = documentSnapshot.getString("surname");
                    fullName = name + " " + surname;
                    fullNameView.setText(fullName);

                    city = documentSnapshot.getString("city");
                    cityTextView.setText(city);

                    skills = (ArrayList<String>) documentSnapshot.get("skills");
                    int skillCount = skills.size();
                    for (int i = 0; i < skillCount; i++) {
                        if (i == 0) {
                            skill1TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                        } else if (i == 1) {
                            LinearLayout layout = root.findViewById(R.id.skill_two);
                            layout.setVisibility(View.VISIBLE);
                            skill2TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                        } else if (i == 2) {
                            Log.i("superproverka", "skill: " + SkillConverter.convertToFullSkill(skills.get(i)));
                            LinearLayout layout = root.findViewById(R.id.skill_three);
                            layout.setVisibility(View.VISIBLE);
                            skill3TextView.setText(SkillConverter.convertToFullSkill(skills.get(i)));
                        }
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }
                        });
                return true;
        }
        return false;
    }

    private void loadImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        getActivity().startActivityForResult(photoPickerIntent, Request.GALLERY);
    }

}