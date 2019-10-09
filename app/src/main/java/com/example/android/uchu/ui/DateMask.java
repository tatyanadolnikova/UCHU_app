package com.example.android.uchu.ui;

import android.text.Editable;
import android.text.TextWatcher;

public class DateMask implements TextWatcher {
    private String currentString = "";
    private String separator;
    private DateMaskingCallback dateMaskingCallback;
    private static final String INVALID_DATE_MSG = "Например: 30.07.1990";

    public DateMask(DateMaskingCallback dateMaskingCallback, String separator) {
        this.dateMaskingCallback = dateMaskingCallback;
        this.separator = separator;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            String dateString = s.toString();
            if (!currentString.equalsIgnoreCase(dateString)) {
                currentString = dateString;
                String errorString = "";
                if (dateString.length() == 2) {
                    if (Integer.parseInt(dateString) < 1 || Integer.parseInt(dateString) > 31) {
                        errorString = INVALID_DATE_MSG;
                    }
                    dateString = dateString + separator;
                } else if (dateString.length() == 5) {
                    String month = dateString.substring(3, 5);
                    if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
                        errorString = INVALID_DATE_MSG;
                    } else {
                        dateString = dateString + separator;
                    }
                } else if (dateString.length() == 10) {
                    String year = dateString.substring(6, 10);
                    if (Integer.parseInt(year) < 1900 || Integer.parseInt(year) > 2100) {
                        errorString = INVALID_DATE_MSG;
                    }
                }
                else if (dateString.length() > 10) {
                    errorString = INVALID_DATE_MSG;
                }
                if (errorString.isEmpty()) {
                    dateMaskingCallback.dateOfBirthValidation(true, dateString, errorString);
                } else {
                    dateMaskingCallback.dateOfBirthValidation(false, dateString, errorString);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public interface DateMaskingCallback {
        void dateOfBirthValidation(final boolean isValid, final String dateOfBirth, final String error) throws Exception;
    }
}