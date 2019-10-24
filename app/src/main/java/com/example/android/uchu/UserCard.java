package com.example.android.uchu;

import com.google.firebase.firestore.DocumentReference;

public class UserCard {

    private String imageUrl;
    private String uName;
    private String userId;

    public UserCard(String imageUrl, String uName) {
        this.imageUrl = imageUrl;
        this.uName = uName;
    }

    public UserCard(String imageUrl, String uName, String userId) {
        this.imageUrl = imageUrl;
        this.uName = uName;
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getuName() {
        return uName;
    }

    public String getUserId() {
        return userId;
    }

}
