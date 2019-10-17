package com.example.android.uchu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.uchu.ui.DateMask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class PersonalInfoActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText city;
    private EditText birthday;
    private FloatingActionButton fab;

    public static final String NAME_KEY = "name";
    public static final String SURNAME_KEY = "surname";
    public static final String CITY_KEY = "city";
    public static final String BIRTHDAY_KEY = "birthday";

    public static FirebaseUser user;
    public static DocumentReference usersDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        user = User.getFirebaseUser();
        usersDoc = FirebaseFirestore.getInstance().document("users/" + user.getUid());

        name = findViewById(R.id.info_name);
        surname = findViewById(R.id.info_surname);
        city = findViewById(R.id.info_city);
        birthday = findViewById(R.id.info_birthday);
        fab = findViewById(R.id.fab);

        birthday.addTextChangedListener(new DateMask(new DateMask.DateMaskingCallback() {
            @Override
            public void dateOfBirthValidation(boolean isValid, String dateOfBirth, String error) throws Exception {
                if (isValid) {
                    birthday.setText(dateOfBirth);
                    birthday.setSelection(dateOfBirth.length());
                } else {
                    birthday.setError(error);
                }
            }
        }, "."));

        birthday.setImeActionLabel("Ok:)", EditorInfo.IME_ACTION_DONE);

        birthday.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addData(name.getText().toString().trim(), surname.getText().toString().trim(),
                            city.getText().toString().trim(), birthday.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData(name.getText().toString().trim(), surname.getText().toString().trim(),
                        city.getText().toString().trim(), birthday.getText().toString().trim());
            }
        });
    }

    public void addData(String name, String surname, String city, String birthday) {
        if (name.equals("") || surname.equals("") || city.equals("") || birthday.equals("")) {
            Log.i("superproverka", "Пустое поле");
            Toast.makeText(this, "Заполните все поля.", Toast.LENGTH_SHORT).show();
            return ;
        }
        Map<String, Object> data = new HashMap<>();
        data.put(NAME_KEY, name);
        data.put(SURNAME_KEY, surname);
        data.put(CITY_KEY, city);
        data.put(BIRTHDAY_KEY, birthday);
        Log.i("superproverka", "\nname: " + name + "\nsurname: " + surname + "\nbirthday: " + birthday + "\ncity: " + city);
        usersDoc.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.i("superproverka", "userDoc has been saved");
                    Intent intent = new Intent(PersonalInfoActivity.this, ChooseSkillActivity.class);
                    startActivity(intent);
                } else {
                    Log.i("superproverka", "userDoc has NOT been saved");
                }
            }
        });
    }

}
