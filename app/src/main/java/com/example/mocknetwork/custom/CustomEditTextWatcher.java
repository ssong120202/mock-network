package com.example.mocknetwork.custom;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.MutableLiveData;

import com.example.mocknetwork.ui.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CustomEditTextWatcher implements TextWatcher {

    public static final int LOGIN_USER_NAME_FIELD = 401;
    public static final int LOGIN_PASSWORD_FIELD = 402;

    private final MutableLiveData<Boolean> observable;
    private final int fieldType;
    private final LoginViewModel loginViewModel;
    private final TextInputEditText editTextView;

    public CustomEditTextWatcher(LoginViewModel loginViewModel, TextInputEditText editTextView, MutableLiveData<Boolean> observable, int fieldType) {
        this.observable = observable;
        this.fieldType = fieldType;
        this.loginViewModel = loginViewModel;
        this.editTextView = editTextView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (observable.getValue() != null && !observable.getValue()) {
            switch (fieldType) {
                case LOGIN_USER_NAME_FIELD:
                    loginViewModel.validateLoginUsername(editTextView.getEditableText().toString().trim());
                    break;
                case LOGIN_PASSWORD_FIELD:
                    loginViewModel.validatePassword(editTextView.getEditableText().toString().trim());
                    break;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
