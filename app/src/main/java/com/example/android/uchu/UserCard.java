package com.example.android.uchu;

public class UserCard {

    private String imageUrl;
    private String uName;

    public UserCard(String uName) {
        this.uName = uName;
    }

    public UserCard(String imageUrl, String uName) {
        this.imageUrl = imageUrl;
        this.uName = uName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getuName() {
        return uName;
    }
}
