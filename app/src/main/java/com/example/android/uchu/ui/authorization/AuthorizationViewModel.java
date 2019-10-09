package com.example.android.uchu.ui.authorization;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthorizationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AuthorizationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}