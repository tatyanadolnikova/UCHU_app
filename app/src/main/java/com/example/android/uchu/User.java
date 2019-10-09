package com.example.android.uchu;

import java.util.ArrayList;

public class User {
    private String email = "default email";
    private String password = "default password";
    private String name = "default name";
    private String surname = "default surname";
    private String birthday = "default birthday";
    private String city = "default city";
    private String skill = "default skill";
    private String info = "Добавить информацию о себе.";
    private ArrayList<Integer> savedContacts;

    public User() {
    }

    public User(String email, String password, String name, String surname, String birthday, String city, String skill) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.city = city;
        this.skill = skill;
    }

    public User(String email, String password, String name, String surname, String birthday, String city, String skill, String info) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.city = city;
        this.skill = skill;
        this.info = info;
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

    public String getSkill() {
        return skill;
    }

    public String getInfo() {
        return info;
    }

    public ArrayList<Integer> getSavedContacts() {
        return savedContacts;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setSavedContacts(ArrayList<Integer> savedContacts) {
        this.savedContacts = savedContacts;
    }
}