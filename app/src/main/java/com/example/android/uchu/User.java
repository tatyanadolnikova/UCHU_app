package com.example.android.uchu;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class User {

    private static FirebaseUser firebaseUser;
    public static User currentUser;

    private String email;
    private String password;
    private String name;
    private String surname;
    private String birthday;
    private String city;
    private ArrayList<String> skills = new ArrayList<>();
    private String info;


    public User(String name, String surname, String birthday, String city) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.city = city;
    }

    public void addSkill(String skill) {
        this.skills.add(skill);
    }

    public static FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public String getInfo() {
        return info;
    }

    public static void setFirebaseUser(FirebaseUser firebaseUser) {
        User.firebaseUser = firebaseUser;
    }
}